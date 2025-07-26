package br.com.danilobandeira29.domain.customer;

import br.com.danilobandeira29.domain.person.Cpf;
import br.com.danilobandeira29.domain.person.Email;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> customerOfId(CustomerId andId);
    Optional<Customer> customerOfCPF(Cpf cpf);
    Optional<Customer> customerOfEmail(Email email);
    Customer create(Customer customer);
    Customer update(Customer customer);
    void deleteAll();
}
