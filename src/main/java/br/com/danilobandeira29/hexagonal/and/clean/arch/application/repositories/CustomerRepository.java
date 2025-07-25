package br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.Customer;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cpf;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> customerOfId(CustomerId andId);
    Optional<Customer> customerOfCPF(Cpf cpf);
    Optional<Customer> customerOfEmail(Email email);
    Customer create(Customer customer);
    Customer update(Customer customer);
    void deleteAll();
}
