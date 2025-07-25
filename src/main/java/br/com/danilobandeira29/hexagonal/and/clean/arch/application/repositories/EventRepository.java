package br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.Event;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventId;

import java.util.Optional;

public interface EventRepository {
    Optional<Event> eventOfId(EventId andId);
    Event create(Event event);
    Event update(Event event);
    void deleteAll();
}
