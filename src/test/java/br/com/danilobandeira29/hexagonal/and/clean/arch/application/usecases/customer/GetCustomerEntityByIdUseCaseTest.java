package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.customer;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repository.InMemoryCustomerRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GetCustomerEntityByIdUseCaseTest {

    @Test
    @DisplayName("Deve obter um cliente por id")
    public void testGetById() {
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var aCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);
        final var expectedId = aCustomer.id().value().toString();
        final var customerRepository = new InMemoryCustomerRepository();
        customerRepository.create(aCustomer);
        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var input = new GetCustomerByIdUseCase.Input(expectedId);
        final var output = useCase.execute(input).get();
        System.out.println(output.email() + " " + output.cpf());
        Assertions.assertEquals(expectedId, output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Deve obter vazio ao tentar recuperar um cliente não existente por id")
    public void testGetByIdWithNoExistentId() {
        final var expectedID = UUID.randomUUID().toString();
        final var customerRepository = new InMemoryCustomerRepository();
        final var useCase = new GetCustomerByIdUseCase(customerRepository);
        final var input = new GetCustomerByIdUseCase.Input(expectedID);
        final var output = useCase.execute(input);
        Assertions.assertTrue(output.isEmpty());
    }

}
