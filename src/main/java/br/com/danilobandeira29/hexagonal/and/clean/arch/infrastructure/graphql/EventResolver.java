package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.graphql;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.event.CreateEventUseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases.event.SubscribeCustomerToEventUseCase;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos.NewEventDTO;
import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos.SubscribeDTO;
import jakarta.transaction.Transactional;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class EventResolver {

    private final CreateEventUseCase createEventUseCase;
    private final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase;

    public EventResolver(
            final CreateEventUseCase createEventUseCase,
            final SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
        this.subscribeCustomerToEventUseCase = Objects.requireNonNull(subscribeCustomerToEventUseCase);
    }

    @MutationMapping
    public CreateEventUseCase.Output createEvent(@Argument NewEventDTO input) {
        return createEventUseCase.execute(new CreateEventUseCase.Input(input.date(), input.name(), input.partnerId(), input.totalSpots()));
    }

    @Transactional
    @MutationMapping
    public SubscribeCustomerToEventUseCase.Output subscribeCustomerToEvent(@Argument SubscribeDTO input) {
        return subscribeCustomerToEventUseCase.execute(new SubscribeCustomerToEventUseCase.Input(input.eventId(), input.customerId()));
    }
}
