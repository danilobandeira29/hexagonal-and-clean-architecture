package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerJpaRepository extends CrudRepository<CustomerEntity, UUID> {

    Optional<CustomerEntity> findByCpf(String cpf);

    Optional<CustomerEntity> findByEmail(String email);
}
