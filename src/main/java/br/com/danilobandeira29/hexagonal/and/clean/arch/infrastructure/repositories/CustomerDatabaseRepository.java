package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.Customer;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cpf;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.CustomerRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.entities.CustomerEntity;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.jpa.repositories.CustomerJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

// Interface Adapter
@Component
public class CustomerDatabaseRepository implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerDatabaseRepository(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
    }

    @Override
    public Optional<Customer> customerOfId(final CustomerId anId) {
        Objects.requireNonNull(anId, "id cannot be null");
        return customerJpaRepository
                .findById(UUID.fromString(anId.value()))
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfCPF(Cpf cpf) {
        Objects.requireNonNull(cpf, "cpf cannot be null");
        return customerJpaRepository
                .findByCpf(cpf.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        Objects.requireNonNull(email, "email cannot be null");
        return customerJpaRepository
                .findByEmail(email.value())
                .map(CustomerEntity::toCustomer);
    }

    @Override
    @Transactional
    public Customer create(Customer customer) {
        return this.customerJpaRepository
                .save(CustomerEntity.of(customer))
                .toCustomer();
    }

    @Override
    public Customer update(Customer customer) {
        return this.customerJpaRepository
                .save(CustomerEntity.of(customer))
                .toCustomer();
    }

    @Override
    public void deleteAll() {
        this.customerJpaRepository.deleteAll();
    }
}
