package br.com.danilobandeira29.infrastructure.jpa.repositories;

import br.com.danilobandeira29.infrastructure.jpa.entities.OutboxEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxJpaRepository extends CrudRepository<OutboxEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timemout", value = "2")
    } )
    List<OutboxEntity> findTop1000ByPublishedFalse();
}
