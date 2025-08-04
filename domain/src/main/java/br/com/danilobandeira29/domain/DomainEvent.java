package br.com.danilobandeira29.domain;

import java.time.Instant;

public interface DomainEvent {
    String domainEventId();
    String type();
    Instant occurredOn();
}
