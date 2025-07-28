package br.com.danilobandeira29.application.application.customer;
import br.com.danilobandeira29.application.customer.CreateCustomerUseCase;
import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.infrastructure.Main;
import br.com.danilobandeira29.infrastructure.jpa.entities.CustomerEntity;
import br.com.danilobandeira29.infrastructure.jpa.repositories.CustomerJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;


@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
public class CreateCustomerEntityUseCaseTestIT {

    @Autowired
    private CreateCustomerUseCase useCase;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @AfterEach
    void teardown() {
        customerJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    public void testCreate() {
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);
        final var output = useCase.execute(createInput);
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedCPF, output.cpf());
        Assertions.assertEquals(expectedEmail, output.email());
        Assertions.assertEquals(expectedName, output.name());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    public void testCreateWithDuplicatedCPFShouldFail() throws Exception {
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";
        var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);
        final var aCustomer = new CustomerEntity();
        aCustomer.setId(UUID.randomUUID());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setEmail(expectedEmail);
        aCustomer.setName(expectedName);
        customerJpaRepository.save(aCustomer);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    public void testCreateWithDuplicatedEmailShouldFail() throws Exception {
        final var expectedCPF = "123.456.789-01" ;
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var expectedError = "Customer already exists";
        final var aCustomer = new CustomerEntity();
        aCustomer.setId(UUID.randomUUID());
        aCustomer.setCpf(expectedCPF);
        aCustomer.setEmail(expectedEmail);
        aCustomer.setName(expectedName);
        customerJpaRepository.save(aCustomer);
        var createInput = new CreateCustomerUseCase.Input(expectedCPF, expectedEmail, expectedName);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(createInput));
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}
