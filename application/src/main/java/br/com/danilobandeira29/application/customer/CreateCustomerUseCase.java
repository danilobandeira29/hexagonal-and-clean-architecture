package br.com.danilobandeira29.application.customer;


import br.com.danilobandeira29.application.UseCase;

import br.com.danilobandeira29.domain.customer.Customer;
import br.com.danilobandeira29.domain.customer.CustomerRepository;
import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.domain.person.Cpf;
import br.com.danilobandeira29.domain.person.Email;

import java.util.Objects;

public class CreateCustomerUseCase extends UseCase<CreateCustomerUseCase.Input, CreateCustomerUseCase.Output> {

    public final CustomerRepository customerRepository;

    public CreateCustomerUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Output execute(Input input) {
        if (customerRepository.customerOfCPF(new Cpf(input.cpf())).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        if (customerRepository.customerOfEmail(new Email(input.email())).isPresent()) {
            throw new ValidationException("Customer already exists");
        }
        var customer = customerRepository.create(Customer.newCustomer(input.name(), input.cpf(), input.email()));
        return new Output(customer.id().value(), customer.cpf().value(), customer.email().value(), customer.name().value());
    }

    public record Input(String cpf, String email, String name) {}

    public record Output(String id, String cpf, String email, String name) {}
}
