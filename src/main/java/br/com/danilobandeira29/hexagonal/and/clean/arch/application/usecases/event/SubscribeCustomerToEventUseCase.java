package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.event;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.UseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.event.EventId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.Ticket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.CustomerRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.EventRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.TicketRepository;


import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    public SubscribeCustomerToEventUseCase(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository,
            final TicketRepository ticketRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(Input input) {
        var aCustomer = customerRepository.customerOfId(CustomerId.with(input.customerId))
                .orElseThrow(() -> new ValidationException("Customer not found"));
        var anEvent = eventRepository.eventOfId(EventId.with(input.eventId))
                .orElseThrow(() -> new ValidationException("Event not found"));
        final Ticket ticket = anEvent.reserveTicket(aCustomer.id());
        ticketRepository.create(ticket);
        eventRepository.update(anEvent);
        return new Output(
                anEvent.id().value(),
                ticket.status().name(),
                ticket.status().name(),
                ticket.reservedAt()
        );
    }

    public record Input(String eventId, String customerId) {}

    public record Output(String eventId, String ticketId, String ticketStatus, Instant reservationDate) {}
}
