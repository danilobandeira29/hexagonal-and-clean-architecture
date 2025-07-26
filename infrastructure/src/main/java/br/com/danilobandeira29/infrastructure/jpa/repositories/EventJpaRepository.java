package br.com.danilobandeira29.infrastructure.jpa.repositories;

import br.com.danilobandeira29.infrastructure.jpa.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventJpaRepository extends CrudRepository<EventEntity, UUID> {

}
