package br.com.danilobandeira29.domain.event;

import br.com.danilobandeira29.domain.exceptions.ValidationException;

import java.util.UUID;

public record EventId(String value) {

    public static EventId unique() {
        return new EventId(UUID.randomUUID().toString());
    }

    public static EventId with(final String value) {
        try {
            return new EventId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for EventId");
        }
    }
}
