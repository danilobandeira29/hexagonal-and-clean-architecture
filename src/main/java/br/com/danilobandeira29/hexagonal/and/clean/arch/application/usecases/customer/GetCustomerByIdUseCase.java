package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.customer;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.UseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.CustomerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repositories.CustomerRepository;
import jakarta.inject.Named;

import java.util.Objects;
import java.util.Optional;

@Named
public class GetCustomerByIdUseCase extends UseCase<GetCustomerByIdUseCase.Input, Optional<GetCustomerByIdUseCase.Output>> {

    public final CustomerRepository customerRepository;

    public GetCustomerByIdUseCase(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return customerRepository.customerOfId(CustomerId.with(input.id()))
                .map(c -> new Output(
                        c.id().value(),
                        c.cpf().value(),
                        c.email().value(),
                        c.name().value()
                        ));
    }

    public record Input(String id) {}

    public record Output(String id, String cpf, String email, String name) {}
}
