package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.Ticket;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.ticket.TicketId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.TicketRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities.TicketEntity;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.repositories.TicketJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class TicketDatabaseRepository implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketDatabaseRepository(final TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = Objects.requireNonNull(ticketJpaRepository);
    }

    @Override
    public Optional<Ticket> ticketOfId(TicketId andId) {
        return this.ticketJpaRepository
                .findById(UUID.fromString(andId.value()))
                .map(TicketEntity::toTicket);
    }

    @Override
    public Ticket create(Ticket ticket) {
        return this.ticketJpaRepository
                .save(TicketEntity.of(ticket))
                .toTicket();
    }

    @Override
    public Ticket update(Ticket ticket) {
        return this.ticketJpaRepository
                .save(TicketEntity.of(ticket))
                .toTicket();
    }
}
