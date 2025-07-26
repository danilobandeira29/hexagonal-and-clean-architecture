package br.com.danilobandeira29.infrastructure.jpa.entities;

import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.ticket.Ticket;
import br.com.danilobandeira29.domain.ticket.TicketId;
import br.com.danilobandeira29.domain.ticket.TicketStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Ticket")
@Table(name = "tickets")
public class TicketEntity {

    @Id
    private UUID id;

    private UUID customerId;
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private Instant paidAt;

    private Instant reservedAt;

    public TicketEntity() {
    }

    public TicketEntity(UUID id, UUID customerId, UUID eventId, TicketStatus status, Instant paidAt, Instant reservedAt) {
        this.id = id;
        this.customerId = customerId;
        this.eventId = eventId;
        this.status = status;
        this.paidAt = paidAt;
        this.reservedAt = reservedAt;
    }

    public static TicketEntity of(Ticket ticket) {
        return new TicketEntity(
                UUID.fromString(ticket.id().value()),
                UUID.fromString(ticket.customerId().value()),
                UUID.fromString(ticket.eventId().value()),
                ticket.status(),
                ticket.paidAt(),
                ticket.reservedAt()
        );
    }

    public Ticket toTicket() {
        return new Ticket(
                TicketId.with(this.id.toString()),
                CustomerId.with(this.customerId.toString()),
                EventId.with(this.eventId.toString()),
                this.status,
                this.paidAt,
                this.reservedAt
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

    public UUID getEventId() {
        return this.eventId;
    }

    public void setEvent(UUID eventId) {
        this.eventId = eventId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public Instant getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Instant reservedAt) {
        this.reservedAt = reservedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketEntity that = (TicketEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(customerId, that.customerId) && Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, eventId);
    }
}
