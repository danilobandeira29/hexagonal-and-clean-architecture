package br.com.fullcycle.hexagonal.graphql;

import br.com.fullcycle.hexagonal.application.application.CreateCustomerUseCase;
import br.com.fullcycle.hexagonal.dtos.CustomerDTO;
import br.com.fullcycle.hexagonal.services.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class CustomerResolver {

    public final CustomerService customerService;

    public CustomerResolver(final CustomerService customerService) {
        this.customerService = Objects.requireNonNull(customerService);
    }

    @MutationMapping
    public CreateCustomerUseCase.Output createCustomer(@Argument CustomerDTO input) {
        final var useCase = new CreateCustomerUseCase(customerService);
        return useCase.execute(new CreateCustomerUseCase.Input(input.getCpf(), input.getEmail(), input.getName()));
    }

}
