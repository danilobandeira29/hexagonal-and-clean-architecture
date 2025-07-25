package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.TicketId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

public class EventTicket {

    private final TicketId id;
    private final EventId eventId;
    private final CustomerId customerId;
    private int ordering;

    public EventTicket(final TicketId id, final EventId eventId, final CustomerId customerId, final Integer ordering) {
        if (id == null) {
            throw new ValidationException("Invalid id for EventTicket");
        }
        if (eventId == null) {
            throw new ValidationException("Invalid eventId for EventTicket");
        }
        this.id = id;
        this.eventId = eventId;
        this.customerId = customerId;
        this.setOrdering(ordering);
    }

    private void setOrdering(final Integer ordering) {
        if (ordering == null) {
            throw new ValidationException("Invalid ordering for EventTicket");
        }
        this.ordering = ordering;
    }

    public TicketId id() {
        return id;
    }

    public EventId eventId() {
        return eventId;
    }

    public int ordering() {
        return ordering;
    }

    public CustomerId customerId() {
        return customerId;
    }
}
