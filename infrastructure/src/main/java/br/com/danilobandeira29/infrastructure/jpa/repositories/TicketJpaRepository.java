package br.com.danilobandeira29.infrastructure.jpa.repositories;

import br.com.danilobandeira29.infrastructure.jpa.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TicketJpaRepository extends CrudRepository<TicketEntity, UUID> { }
