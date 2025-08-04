package br.com.danilobandeira29.domain.event;

import br.com.danilobandeira29.domain.customer.Customer;
import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.domain.partner.Partner;
import br.com.danilobandeira29.domain.ticket.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeFormatter;

public class EventTest {

    @Test
    @DisplayName("Deve resevar um ticket para um cliente")
    public void testReserveTicketForCustomer() {
        final var expectedDate = "2025-01-01";
        final var expectedTicketOrder = 1;
        final var expectedDomainEvent = "event-ticket.reserved";
        final var aPartner = Partner.newPartner("Partner Name", "41.536.538/0001-00", "partner@email.com");
        final var anEvent = Event.newEvent("Event name", expectedDate, 10, aPartner);
        final var aCustomer = Customer.newCustomer( "John Doe", "123.456.789-01", "john.doe@gmail.com");
        final var ticket = anEvent.reserveTicket(aCustomer.id());
        final var actualTicketEvent = anEvent.allTickets().iterator().next();
        Assertions.assertNotNull(ticket.eventTicketId());
        Assertions.assertEquals(expectedDate, anEvent.date().format(DateTimeFormatter.ISO_LOCAL_DATE));
        Assertions.assertEquals(anEvent.id(), ticket.eventId());
        Assertions.assertEquals(aCustomer.id(), ticket.customerId());
        Assertions.assertEquals(expectedTicketOrder, actualTicketEvent.ordering());
        Assertions.assertEquals(ticket.eventTicketId(), actualTicketEvent.eventTicketId());
        final var actualDomainEvents = anEvent.allDomainEvents().iterator().next();
        Assertions.assertEquals(expectedDomainEvent, actualDomainEvents.type());
    }

    @Test
    @DisplayName("Não deve reservar para o cliente quando o evento já está esgotado")
    public void testShouldNotReserveTicketForCustomerWhenEventIsSoldOut() {
        final var expectedError = "Event sold out";
        final var aPartner = Partner.newPartner("Partner Name", "41.536.538/0001-00", "partner@email.com");
        final var anEvent = Event.newEvent("Event name", "2025-01-01", 1, aPartner);
        final var aCustomer = Customer.newCustomer( "John Doe", "123.456.789-01", "john.doe@gmail.com");
        final var customer = Customer.newCustomer( "John Doe 2", "123.456.000-22", "john.doe2@gmail.com");
        anEvent.reserveTicket(aCustomer.id());
        final var actualError = Assertions.assertThrows(ValidationException.class, () -> anEvent.reserveTicket(customer.id()));
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }

    @Test
    @DisplayName("Não deve reservar 2 tickets para o mesmo cliente")
    public void testShouldNotReserveTwoTicketsForSameCustomer() {
        final var expectedError = "Email already exists";
        final var aPartner = Partner.newPartner("Partner Name", "41.536.538/0001-00", "partner@email.com");
        final var anEvent = Event.newEvent("Event name", "2025-01-01", 10, aPartner);
        final var aCustomer = Customer.newCustomer( "John Doe", "123.456.789-01", "john.doe@gmail.com");
        anEvent.reserveTicket(aCustomer.id());
        final var actualError = Assertions.assertThrows(ValidationException.class, () -> anEvent.reserveTicket(aCustomer.id()));
        Assertions.assertEquals(expectedError, actualError.getMessage());
    }
}
