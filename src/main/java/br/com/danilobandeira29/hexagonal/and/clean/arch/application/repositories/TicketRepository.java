package br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.Ticket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.TicketId;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> ticketOfId(TicketId andId);
    Ticket create(Ticket ticket);
    Ticket update(Ticket ticket);
}
