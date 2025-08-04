package br.com.danilobandeira29.infrastructure.jpa.entities;

import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventTicket;
import br.com.danilobandeira29.domain.event.EventTicketId;
import br.com.danilobandeira29.domain.ticket.TicketId;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID eventTicketId;
    private UUID ticketId;

    private UUID customerId;


    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(final UUID eventTicketId, final UUID customerId, final int ordering,  final UUID ticketId, final EventEntity event) {
        this.eventTicketId = eventTicketId;
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.event = event;
        this.ordering = ordering;
    }

    public static EventTicketEntity of(final EventEntity event, final EventTicket ticket) {
        return new EventTicketEntity(
                UUID.fromString(ticket.eventTicketId().value()),
                UUID.fromString(ticket.customerId().value()),
                ticket.ordering(),
                ticket.ticketId() != null ? UUID.fromString(ticket.ticketId().value()) : null,
                event
        );
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                EventTicketId.with(eventTicketId.toString()),
                EventId.with(this.event.getId().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ticketId != null ? TicketId.with(this.eventTicketId.toString()) : null,
                this.ordering
        );
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public void setCustomer(UUID customerId) {
        this.customerId = customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EventTicketEntity that = (EventTicketEntity) o;
        return Objects.equals(eventTicketId, that.eventTicketId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventTicketId);
    }

    public void setEventTicketId(UUID eventTicketId) {
        this.eventTicketId = eventTicketId;
    }
}
