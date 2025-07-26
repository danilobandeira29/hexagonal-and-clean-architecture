package br.com.danilobandeira29.repository;


import br.com.danilobandeira29.domain.customer.Customer;
import br.com.danilobandeira29.domain.customer.CustomerId;
import br.com.danilobandeira29.domain.customer.CustomerRepository;
import br.com.danilobandeira29.domain.person.Cpf;
import br.com.danilobandeira29.domain.person.Email;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCustomerRepository implements CustomerRepository {

    private Map<String, Customer> customers;
    private Map<String, Customer> customersByCpf;
    private Map<String, Customer> customersByEmail;

    public InMemoryCustomerRepository() {
        this.customers = new HashMap<>();
        this.customersByCpf = new HashMap<>();
        this.customersByEmail = new HashMap<>();
    }

    @Override
    public Optional<Customer> customerOfId(CustomerId andId) {
        return Optional.ofNullable(this.customers.get(Objects.requireNonNull(andId).value()));
    }

    @Override
    public Optional<Customer> customerOfCPF(Cpf cpf) {
        return Optional.ofNullable(this.customersByCpf.get(Objects.requireNonNull(cpf.value())));
    }

    @Override
    public Optional<Customer> customerOfEmail(Email email) {
        return Optional.ofNullable(this.customersByEmail.get(Objects.requireNonNull(email.value())));
    }

    @Override
    public Customer create(Customer customer) {
        this.customers.put(customer.id().value(), customer);
        this.customersByCpf.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        this.customers.put(customer.id().value(), customer);
        this.customersByCpf.put(customer.cpf().value(), customer);
        this.customersByEmail.put(customer.email().value(), customer);
        return customer;
    }

    @Override
    public void deleteAll() {
        this.customers = new HashMap<>();
        this.customersByCpf = new HashMap<>();
        this.customersByEmail = new HashMap<>();
    }
}