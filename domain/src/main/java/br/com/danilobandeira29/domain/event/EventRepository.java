package br.com.danilobandeira29.domain.event;

import java.util.Optional;

public interface EventRepository {
    Optional<Event> eventOfId(EventId andId);
    Event create(Event event);
    Event update(Event event);
    void deleteAll();
}
