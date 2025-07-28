package br.com.danilobandeira29.domain.customer;

import br.com.danilobandeira29.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerEntityTest {

    @Test
    @DisplayName("Deve instanciar um cliente")
    public void testCreateCustomer() {
        final var expectedCPF = "123.456.789-01";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var aCustomer = Customer.newCustomer(expectedName, expectedCPF, expectedEmail);
        Assertions.assertNotNull(aCustomer.id());
        Assertions.assertEquals(expectedCPF, aCustomer.cpf().value());
        Assertions.assertEquals(expectedEmail, aCustomer.email().value());
        Assertions.assertEquals(expectedName, aCustomer.name().value());
    }

    @Test
    @DisplayName("Deve não instanciar um cliente com CPF inválido")
    public void testCreateCustomerWithInvalidCPF() {
        final var expectedError = "Invalid value for Cpf";
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer( "John Doe", "123.456789-01", "john.doe@gmail.com")
        );
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Deve não instanciar um cliente com Name inválido")
    public void testCreateCustomerWithInvalidName() {
        final var expectedError = "Invalid value for Name";
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Customer.newCustomer( "", "123.456.789-01", "john.doe@gmail.com")
        );
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
