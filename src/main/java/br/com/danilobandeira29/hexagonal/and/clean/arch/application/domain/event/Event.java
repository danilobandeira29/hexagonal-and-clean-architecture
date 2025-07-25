package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Name;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.Ticket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Event {

    private static final int ONE = 1;
    private final EventId id;
    private Name name;
    private LocalDate date;
    private int totalSpots;
    private PartnerId partnerId;
    private Set<EventTicket> tickets;

    public Event(
            final EventId id,
            final String name,
            final String date,
            final Integer totalSpots,
            final PartnerId partnerId,
            final Set<EventTicket> tickets
    ) {
        this(id, tickets);
        this.setName(name);
        this.setDate(date);
        this.setTotalSpots(totalSpots);
        this.setPartnerId(partnerId);
    }

    private Event(final EventId id, final Set<EventTicket> tickets) {
        if (id == null) {
            throw new ValidationException("Invalid id for Event");
        }
        this.id = id;
        this.tickets = tickets != null ? tickets : new HashSet<>(0);
    }

    public static Event restore(
            final String id,
            final String name,
            final String date,
            final int totalSpots,
            final String partnerId,
            final Set<EventTicket> tickets) {
        return new Event(EventId.with(id), name, date, totalSpots, PartnerId.with(partnerId), tickets);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static Event newEvent(final String name, final String date, final Integer totalSpots, final Partner partner) {
        return new Event(EventId.unique(), name, date, totalSpots, partner.id(), new HashSet<>(0));
    }

    public EventId id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public LocalDate date() {
        return date;
    }

    public int totalSpots() {
        return totalSpots;
    }

    public PartnerId partnerId() {
        return partnerId;
    }

    public Set<EventTicket> allTickets() {
        return Collections.unmodifiableSet(tickets);
    }

    private void setName(final String name) {
        this.name = new Name(name);
    }

    private void setDate(final String date) {
        if (date == null) {
            throw new ValidationException("Invalid date for Event");
        }
        try {
            this.date = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (RuntimeException ex) {
            throw new ValidationException("Invalid date for Event", ex);
        }
    }

    private void setPartnerId(final PartnerId partnerId) {
        if (partnerId == null) {
            throw new ValidationException("Invalid partnerId for Event");
        }
        this.partnerId = partnerId;
    }

    private void setTotalSpots(final Integer totalSpots) {
        if (totalSpots == null) {
            throw new ValidationException("Invalid totalSpots for Event");
        }
        this.totalSpots = totalSpots;
    }

    public Ticket reserveTicket(final CustomerId customerId) {
       this.allTickets()
                .stream()
                .filter(it -> Objects.equals(it.customerId(), customerId))
                .findFirst().ifPresent(it -> {
                    throw new ValidationException("Email already exists");
                });
        if (totalSpots() < allTickets().size() + ONE) {
            throw new ValidationException("Event sold out");
        }
        final var newTicket = Ticket.newTicket(customerId, id());
        this.tickets.add(
                new EventTicket(
                        newTicket.id(),
                        newTicket.eventId(),
                        newTicket.customerId(),
                        allTickets().size()+1
                )
        );
        return newTicket;
    }
}
