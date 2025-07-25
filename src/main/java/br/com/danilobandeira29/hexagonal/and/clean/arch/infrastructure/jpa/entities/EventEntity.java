package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.Event;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventTicket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.Ticket;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "Event")
@Table(name = "events")
public class EventEntity {

    @Id
    private UUID id;

    private String name;

    private LocalDate date;

    private int totalSpots;

    private UUID partnerId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "event")
    private Set<EventTicketEntity> tickets;

    public EventEntity() {
        this.tickets = new HashSet<>();
    }

    public EventEntity(UUID id, String name, LocalDate date, int totalSpots, UUID partnerId) {
        this();
        this.id = id;
        this.name = name;
        this.date = date;
        this.totalSpots = totalSpots;
        this.partnerId = partnerId;
    }

    public static EventEntity of(Event event) {
        final var entity = new EventEntity(
                UUID.fromString(event.id().value()),
                event.name().value(),
                event.date(),
                event.totalSpots(),
                UUID.fromString(event.partnerId().value())
        );
        event.allTickets().forEach(entity::addTicket);
        return entity;
    }

    public Event toEvent() {
        return Event.restore(
                this.id.toString(),
                this.name,
                this.date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                this.totalSpots,
                this.partnerId.toString(),
                this.tickets.stream().map(EventTicketEntity::toEventTicket).collect(Collectors.toSet())
        );
    }

    private void addTicket(final EventTicket ticket) {
        this.tickets.add(EventTicketEntity.of(this, ticket));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    public void setTickets(Set<EventTicketEntity> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity eventEntity = (EventEntity) o;
        return Objects.equals(id, eventEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
