package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.event;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repository.InMemoryEventRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.repository.InMemoryPartnerRepository;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.Partner;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.partner.PartnerId;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateEventUseCaseTest {

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {
        final var aPartner = Partner.newPartner("Partner name", "41.536.538/0001-00", "partner@email.com");
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 18;
        final var expectedPartnerId = aPartner.id();
        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();
        partnerRepository.create(aPartner);
        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
        final var input = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId.value(), expectedTotalSpots);
        final var output = useCase.execute(input);
        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(expectedDate, output.date());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedTotalSpots, output.totalSpots());
        Assertions.assertEquals(expectedPartnerId.value(), output.partnerId());
    }

    @Test
    @DisplayName("Não deve criar um evento quando o Partner não for encontrado")
    public void testCreateEvent_whenPartnerDoesntExists_ShouldThrowError() throws Exception {
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 18;
        final var expectedPartnerId = PartnerId.unique();
        final var expectedError = "Partner not found";
        final var eventRepository = new InMemoryEventRepository();
        final var partnerRepository = new InMemoryPartnerRepository();
        final var useCase = new CreateEventUseCase(eventRepository, partnerRepository);
        final var input = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId.value(), expectedTotalSpots);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(input));
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}