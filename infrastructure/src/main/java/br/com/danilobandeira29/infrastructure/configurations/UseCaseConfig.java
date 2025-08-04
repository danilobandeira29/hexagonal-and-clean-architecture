package br.com.danilobandeira29.infrastructure.configurations;

import br.com.danilobandeira29.application.customer.CreateCustomerUseCase;
import br.com.danilobandeira29.application.customer.GetCustomerByIdUseCase;
import br.com.danilobandeira29.application.ticket.CreateTicketForCustomerUseCase;
import br.com.danilobandeira29.domain.customer.CustomerRepository;
import br.com.danilobandeira29.domain.event.EventRepository;
import br.com.danilobandeira29.domain.partner.PartnerRepository;
import br.com.danilobandeira29.domain.ticket.TicketRepository;
import br.com.danilobandeira29.application.event.CreateEventUseCase;
import br.com.danilobandeira29.application.event.SubscribeCustomerToEventUseCase;
import br.com.danilobandeira29.application.partner.CreatePartnerUseCase;
import br.com.danilobandeira29.application.partner.GetPartnerByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CustomerRepository customerRepository;
    private final EventRepository eventRepository;
    private final PartnerRepository partnerRepository;
    private final TicketRepository ticketRepository;

    public UseCaseConfig(
            final CustomerRepository customerRepository,
            final EventRepository eventRepository,
            final PartnerRepository partnerRepository,
            final TicketRepository ticketRepository
    ) {
        this.customerRepository = Objects.requireNonNull(customerRepository);
        this.eventRepository = Objects.requireNonNull(eventRepository);
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(customerRepository);
    }

    @Bean
    public CreateEventUseCase createEventUseCase() {
        return new CreateEventUseCase(eventRepository, partnerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    public GetPartnerByIdUseCase getPartnerByIdUseCase() {
        return new GetPartnerByIdUseCase(partnerRepository);
    }

    @Bean
    public CreatePartnerUseCase createPartnerUseCase() {
        return new CreatePartnerUseCase(partnerRepository);
    }

    @Bean
    public SubscribeCustomerToEventUseCase subscribeCustomerToEventUseCase() {
        return new SubscribeCustomerToEventUseCase(customerRepository, eventRepository);
    }

    @Bean
    public CreateTicketForCustomerUseCase createTicketForCustomerUseCase() {
        return new CreateTicketForCustomerUseCase(ticketRepository);
    }
}
