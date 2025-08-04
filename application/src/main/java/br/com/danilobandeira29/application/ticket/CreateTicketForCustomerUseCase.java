package br.com.danilobandeira29.application.ticket;

import br.com.danilobandeira29.application.UseCase;
import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.event.EventId;
import br.com.danilobandeira29.domain.event.EventTicketId;
import br.com.danilobandeira29.domain.ticket.Ticket;
import br.com.danilobandeira29.domain.ticket.TicketRepository;

import java.util.Objects;

public class CreateTicketForCustomerUseCase extends
        UseCase<CreateTicketForCustomerUseCase.Input, CreateTicketForCustomerUseCase.Output> {

   private final TicketRepository ticketRepository;

    public CreateTicketForCustomerUseCase(final TicketRepository ticketRepository) {
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Override
    public Output execute(Input input) {
        final var aTicket =
                Ticket.newTicket(EventTicketId.with(input.eventTicketId), CustomerId.with(input.customerId), EventId.with(input.eventId));
        this.ticketRepository.create(aTicket);
        return new Output(aTicket.id().value());
    }

    public record Input(String eventTicketId, String eventId, String customerId) { }
    public record Output(String ticketId) { }
}
