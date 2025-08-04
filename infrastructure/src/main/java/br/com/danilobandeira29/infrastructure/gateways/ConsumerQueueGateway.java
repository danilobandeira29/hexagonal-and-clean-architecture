package br.com.danilobandeira29.infrastructure.gateways;

import br.com.danilobandeira29.application.ticket.CreateTicketForCustomerUseCase;
import br.com.danilobandeira29.domain.event.EventTicketReserved;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ConsumerQueueGateway implements QueueGateway {

    private final CreateTicketForCustomerUseCase createTicketForCustomerUseCase;
    private final ObjectMapper mapper;

    public ConsumerQueueGateway(
            final CreateTicketForCustomerUseCase createTicketForCustomerUseCase,
            final ObjectMapper mapper
    ) {
        this.createTicketForCustomerUseCase = Objects.requireNonNull(createTicketForCustomerUseCase);
        this.mapper = Objects.requireNonNull(mapper);
    }

    @Async(value = "queueExecutor")
    @Override
    public void publish(String content) {
        if (content == null) {
            return;
        }
        if (content.contains("event-ticket.reserved")) {
            try {
                final var dto = this.mapper.readValue(content, EventTicketReserved.class);
                this.createTicketForCustomerUseCase.execute(new CreateTicketForCustomerUseCase.Input(dto.eventTicketId(), dto.eventId(), dto.customerId()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
