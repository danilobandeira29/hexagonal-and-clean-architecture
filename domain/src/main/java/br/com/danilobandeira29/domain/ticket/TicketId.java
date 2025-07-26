package br.com.danilobandeira29.domain.ticket;

import br.com.danilobandeira29.domain.exceptions.ValidationException;

import java.util.UUID;

public record TicketId(String value) {
    public static TicketId unique() {
        return new TicketId(UUID.randomUUID().toString());
    }

    public static TicketId with(final String value) {
        try {
            return new TicketId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for TicketId");
        }
    }
}
