package br.com.danilobandeira29.domain.partner;

import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.domain.partner.Partner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PartnerTest {

    @Test
    @DisplayName("Deve instanciar um parceiro")
    public void testCreatePartner() {
        final var expectedCNPJ = "41.536.538/0001-00";
        final var expectedEmail = "john.doe@gmail.com";
        final var expectedName = "John Doe";
        final var aPartner = Partner.newPartner(expectedName, expectedCNPJ, expectedEmail);
        Assertions.assertNotNull(aPartner.id());
        Assertions.assertEquals(expectedCNPJ, aPartner.cnpj().value());
        Assertions.assertEquals(expectedEmail, aPartner.email().value());
        Assertions.assertEquals(expectedName, aPartner.name().value());
    }

    @Test
    @DisplayName("Deve não instanciar um parceiro com CNPJ inválido")
    public void testCreatePartnerWithInvalidCNPJ() {
        final var expectedError = "Invalid value for Cnpj";
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner( "John Doe", "41.536538/0001-00", "john.doe@gmail.com")
        );
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Deve não instanciar um parceiro com Name inválido")
    public void testCreatePartnerWithInvalidName() {
        final var expectedError = "Invalid value for Name";
        final var actualError = Assertions.assertThrows(
                ValidationException.class,
                () -> Partner.newPartner( "", "41.536.538/0001-00", "john.doe@gmail.com")
        );
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
