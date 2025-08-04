package br.com.danilobandeira29.infrastructure.jpa.repositories;

import br.com.danilobandeira29.infrastructure.jpa.entities.OutboxEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxJpaRepository extends CrudRepository<OutboxEntity, UUID> {

    List<OutboxEntity> findAllByPublishedFalse();
}
