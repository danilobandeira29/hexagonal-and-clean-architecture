package br.com.danilobandeira29.domain.ticket;

import br.com.danilobandeira29.domain.DomainEvent;
import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventTicketId;

import java.time.Instant;
import java.util.UUID;

public record TicketCreated(
        String domainEventId,
        String type,
        String ticketId,
        String eventTicketId,
        String eventId,
        String customerId,
        Instant occurredOn
) implements DomainEvent {
    public TicketCreated(TicketId ticketId, EventTicketId eventTicketId, EventId eventId, CustomerId customerId) {
        this(UUID.randomUUID().toString(), "ticket.created", ticketId.value(), eventTicketId.value(), eventId.value(), customerId.value(), Instant.now());
    }
}
