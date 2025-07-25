package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Cpf;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Email;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person.Name;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

import java.util.Objects;

// Customer as Aggregate
public class Customer {

    private final CustomerId id;
    private Name name;
    private Cpf cpf;
    private Email email;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Customer(final CustomerId customerId, final String name, final String cpf, final String email) {
        if (customerId == null) {
            throw new ValidationException("Invalid customerId for Customer");
        }
        this.id = customerId;
        this.setName(name);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public static Customer newCustomer(String name, String cpf, String email) {
        return new Customer(CustomerId.unique(), name, cpf, email);
    }

    public CustomerId id() {
        return id;
    }

    public Name name() {
        return name;
    }

    public Cpf cpf() {
        return cpf;
    }

    public Email email() {
        return email;
    }

    private void setCpf(final String cpf) {
        this.cpf = new Cpf(cpf);
    }

    private void setEmail(final String email) {
        this.email = new Email(email);
    }

    private void setName(final String name) {
        this.name = new Name(name);
    }
}
