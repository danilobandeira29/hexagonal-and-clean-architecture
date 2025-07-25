package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventTicket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.TicketId;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity(name = "EventTicket")
@Table(name = "events_tickets")
public class EventTicketEntity {

    @Id
    private UUID id;

    private UUID customerId;
    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    private EventEntity event;

    public EventTicketEntity() {
    }

    public EventTicketEntity(final UUID id, final UUID customerId, final int ordering, final EventEntity event) {
        this.id = id;
        this.customerId = customerId;
        this.event = event;
        this.ordering = ordering;
    }

    public static EventTicketEntity of(final EventEntity event, final EventTicket ticket) {
        return new EventTicketEntity(
                UUID.fromString(ticket.id().value()),
                UUID.fromString(ticket.customerId().value()),
                ticket.ordering(),
                event
        );
    }

    public EventTicket toEventTicket() {
        return new EventTicket(
                TicketId.with(this.id.toString()),
                EventId.with(this.event.getId().toString()),
                CustomerId.with(this.customerId.toString()),
                this.ordering
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        return ordering == that.ordering && Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, ordering, event);
    }
}
