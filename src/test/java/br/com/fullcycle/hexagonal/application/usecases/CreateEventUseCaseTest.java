package br.com.fullcycle.hexagonal.application.usecases;

import br.com.fullcycle.hexagonal.application.exceptions.ValidationException;
import br.com.fullcycle.hexagonal.models.Event;
import br.com.fullcycle.hexagonal.models.Partner;
import br.com.fullcycle.hexagonal.services.EventService;
import br.com.fullcycle.hexagonal.services.PartnerService;
import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateEventUseCaseTest {

    @Test
    @DisplayName("Deve criar um evento")
    public void testCreate() throws Exception {
        final var expectedDate = "2021-01-01";
        final var expectedName = "Disney on Ice";
        final var expectedTotalSpots = 18;
        final var expectedPartnerId = TSID.fast().toLong();
        final var eventService = Mockito.mock(EventService.class);
        final var partnerService = Mockito.mock(PartnerService.class);
        final var useCase = new CreateEventUseCase(eventService, partnerService);
        when(eventService.save(any()))
                .thenAnswer(a -> {
                    final var e = a.getArgument(0, Event.class);
                    e.setId(TSID.fast().toLong());
                    return e;
                 });
        when(partnerService.findById(expectedPartnerId))
                .thenReturn(Optional.of(new Partner()));
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
        final var expectedPartnerId = TSID.fast().toLong();
        final var expectedError = "Partner not found";
        final var eventService = Mockito.mock(EventService.class);
        final var partnerService = Mockito.mock(PartnerService.class);
        final var useCase = new CreateEventUseCase(eventService, partnerService);
        when(partnerService.findById(expectedPartnerId))
                .thenReturn(Optional.empty());
        final var input = new CreateEventUseCase.Input(expectedDate, expectedName, expectedPartnerId, expectedTotalSpots);
        final var actualException = Assertions.assertThrows(ValidationException.class, () -> useCase.execute(input));
        Assertions.assertEquals(expectedError, actualException.getMessage());
    }
}