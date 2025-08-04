package br.com.danilobandeira29.infrastructure.repositories;

import br.com.danilobandeira29.domain.DomainEvent;
import br.com.danilobandeira29.domain.event.Event;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventRepository;
import br.com.danilobandeira29.infrastructure.jpa.entities.EventEntity;
import br.com.danilobandeira29.infrastructure.jpa.entities.OutboxEntity;
import br.com.danilobandeira29.infrastructure.jpa.repositories.EventJpaRepository;
import br.com.danilobandeira29.infrastructure.jpa.repositories.OutboxJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final OutboxJpaRepository outboxJpaRepository;
    private final ObjectMapper mapper;

    public EventDatabaseRepository(
            final EventJpaRepository eventJpaRepository,
            final OutboxJpaRepository outboxJpaRepository,
            final ObjectMapper mapper
    ) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
        this.outboxJpaRepository = Objects.requireNonNull(outboxJpaRepository);
        this.mapper = mapper;
    }

    @Override
    public Optional<Event> eventOfId(EventId andId) {
        Objects.requireNonNull(andId, "id cannot be null");
        return this.eventJpaRepository
                .findById(UUID.fromString(andId.value()))
                .map(EventEntity::toEvent);
    }

    @Override
    public Event create(Event event) {
        this.outboxJpaRepository.saveAll(
            event.allDomainEvents().stream()
                    .map(it -> OutboxEntity.of(it, this::toJson))
                    .toList()
        );
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    private String toJson(DomainEvent domainEvent) {
        try {
            return this.mapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Event update(Event event) {
        this.outboxJpaRepository.saveAll(
                event.allDomainEvents().stream()
                        .map(it -> OutboxEntity.of(it, this::toJson))
                        .toList()
        );
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    public void deleteAll() {
        this.eventJpaRepository.deleteAll();
    }
}
