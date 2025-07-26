package br.com.danilobandeira29.domain.ticket;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> ticketOfId(TicketId andId);
    Ticket create(Ticket ticket);
    Ticket update(Ticket ticket);
}
