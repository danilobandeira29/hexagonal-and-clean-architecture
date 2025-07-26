package br.com.danilobandeira29.application.event;

import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.domain.partner.Partner;
import br.com.danilobandeira29.domain.partner.PartnerId;
import br.com.danilobandeira29.event.CreateEventUseCase;
import br.com.danilobandeira29.infrastructure.Main;
import br.com.danilobandeira29.infrastructure.jpa.entities.PartnerEntity;
import br.com.danilobandeira29.infrastructure.jpa.repositories.EventJpaRepository;
import br.com.danilobandeira29.infrastructure.jpa.repositories.PartnerJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
class CreateEventUseCaseTestIT {

    @Autowired
    private CreateEventUseCase useCase;

    @Autowired
    private PartnerJpaRepository partnerJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @AfterEach
    void teardown() {
        eventJpaRepository.deleteAll();
        partnerJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {
        final var aPartner = Partner.newPartner("Parner Name", "41.536.538/0001-00", "partner@email.com");
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 18;
        final var expectedPartnerId = aPartner.id().value();
        partnerJpaRepository.save(PartnerEntity.of(aPartner));
        final var input = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);
        final var output = useCase.execute(input);
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId, output.partnerId());
    }

    @Test
    @DisplayName("Não deve criar um evento quando o Partner não for encontrado")
    public void testCreateEvent_whenPartnerDoesntExists_ShouldThrowError() throws Exception {
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 18;
        final var expectedPartnerId = PartnerId.unique().value();
        final var expectedError = "Partner not found";
        final var input = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(input));
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}