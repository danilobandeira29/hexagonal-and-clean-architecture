package br.com.danilobandeira29.domain.ticket;

import br.com.danilobandeira29.domain.DomainEvent;
import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventTicketId;
import br.com.danilobandeira29.domain.exceptions.ValidationException;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Ticket {

    private final TicketId id;
    private CustomerId customerId;
    private EventId eventId;
    private TicketStatus status;
    private Instant paidAt;
    private Instant reservedAt;
    private final Set<DomainEvent> domainEvents;

    public Ticket(
            final TicketId id,
            final CustomerId customerId,
            final EventId eventId,
            final TicketStatus status,
            final Instant paidAt,
            final Instant reservedAt
    ) {
        this.id = id;
        this.setCustomerId(customerId);
        this.setEventId(eventId);
        this.setStatus(status);
        this.setPaidAt(paidAt);
        this.setReservedAt(reservedAt);
        this.domainEvents = new HashSet<>();
    }

    public Set<DomainEvent> allDomainEvents() {
        return Collections.unmodifiableSet(domainEvents);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Ticket newTicket(final CustomerId customerId, final EventId eventId) {
        return new Ticket(TicketId.unique(), customerId, eventId, TicketStatus.PENDING, null, Instant.now());
    }

    public static Ticket newTicket(final EventTicketId eventTicketId, final CustomerId customerId, final EventId eventId) {
        final var aTicket = newTicket(customerId, eventId);
        aTicket.domainEvents.add(new TicketCreated(aTicket.id, eventTicketId, eventId, customerId));
        return aTicket;
    }

    public TicketId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public EventId eventId() {
        return eventId;
    }

    public TicketStatus status() {
        return status;
    }

    public Instant paidAt() {
        return paidAt;
    }

    public Instant reservedAt() {
        return reservedAt;
    }

    private void setCustomerId(CustomerId customerId) {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Ticket");
        }
        this.customerId = customerId;
    }

    private void setEventId(EventId eventId) {
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for Ticket");
        }
        this.eventId = eventId;
    }

    private void setStatus(TicketStatus status) {
        if (status == null) {
            throw new ValidationException("Invalid status for Ticket");
        }
        this.status = status;
    }

    private void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    private void setReservedAt(Instant reservedAt) {
        if (reservedAt == null) {
            throw new ValidationException("Invalid reservedAt for Ticket");
        }
        this.reservedAt = reservedAt;
    }
}
