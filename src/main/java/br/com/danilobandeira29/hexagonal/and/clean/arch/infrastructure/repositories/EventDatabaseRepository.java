package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.Event;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.EventRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities.EventEntity;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.repositories.EventJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    public EventDatabaseRepository(final EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
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
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    public Event update(Event event) {
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    public void deleteAll() {
        this.eventJpaRepository.deleteAll();
    }
}
