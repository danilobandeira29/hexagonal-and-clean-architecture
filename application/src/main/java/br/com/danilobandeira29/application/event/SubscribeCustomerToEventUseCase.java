package br.com.danilobandeira29.application.event;

import br.com.danilobandeira29.application.UseCase;
import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.customer.CustomerRepository;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventRepository;
import br.com.danilobandeira29.domain.event.EventTicket;
import br.com.danilobandeira29.domain.exceptions.ValidationException;


import java.time.Instant;
import java.util.Objects;

public class SubscribeCustomerToEventUseCase extends UseCase<SubscribeCustomerToEventUseCase.Input, SubscribeCustomerToEventUseCase.Output> {
    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;

    public SubscribeCustomerToEventUseCase(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }

    @Override
    public Output execute(Input input) {
        var aCustomer = customerRepository.customerOfId(CustomerId.with(input.customerId))
                .orElseThrow(() -> new ValidationException("Customer not found"));
        var anEvent = eventRepository.eventOfId(EventId.with(input.eventId))
                .orElseThrow(() -> new ValidationException("Event not found"));
        final EventTicket eventTicket = anEvent.reserveTicket(aCustomer.id());
        eventRepository.update(anEvent);
        return new Output(
                anEvent.id().value(),
                eventTicket.eventTicketId().value(),
                Instant.now()
        );
    }

    public record Input(String eventId, String customerId) {}

    public record Output(String eventId, String eventTicketId, Instant reservationDate) {}
}
