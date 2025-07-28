package br.com.danilobandeira29.application.repository;



import br.com.danilobandeira29.domain.event.Event;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryEventRepository implements EventRepository {

    private Map<String, Event> events;

    public InMemoryEventRepository() {
        this.events = new HashMap<>();
    }
    @Override
    public Optional<Event> eventOfId(EventId andId) {
        return Optional.ofNullable(this.events.get(Objects.requireNonNull(andId).value()));
    }

    @Override
    public Event create(Event event) {
        this.events.put(event.id().value(), event);
        return event;
    }

    @Override
    public Event update(Event event) {
        this.events.put(event.id().value(), event);
        return event;
    }

    @Override
    public void deleteAll() {
        this.events = new HashMap<>();
    }
}