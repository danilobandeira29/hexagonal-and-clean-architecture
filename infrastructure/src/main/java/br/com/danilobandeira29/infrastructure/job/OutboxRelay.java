package br.com.danilobandeira29.infrastructure.job;

import br.com.danilobandeira29.infrastructure.gateways.QueueGateway;
import br.com.danilobandeira29.infrastructure.jpa.repositories.OutboxJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxRelay {

    private static final int TWO_SECONDS = 2_000;
    private final OutboxJpaRepository outboxJpaRepository;
    private final QueueGateway queueGateway;

    public OutboxRelay(final OutboxJpaRepository outboxJpaRepository, final QueueGateway queueGateway) {
        this.outboxJpaRepository = outboxJpaRepository;
        this.queueGateway = queueGateway;
    }


    @Scheduled(fixedRate = TWO_SECONDS)
    @Transactional
    void execute() {
        this.outboxJpaRepository.findTop1000ByPublishedFalse()
                .forEach(it -> {
                    this.queueGateway.publish(it.getContent());
                    this.outboxJpaRepository.save(it.notePublished());
                });
    }
}
