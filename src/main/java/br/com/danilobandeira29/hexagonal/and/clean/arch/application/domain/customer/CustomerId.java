package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.customer;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

import java.util.UUID;

public record CustomerId(String value) {

    public static CustomerId unique() {
        return new CustomerId(UUID.randomUUID().toString());
    }

    public static CustomerId with(final String value) {
        try {
            return new CustomerId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for CustomerId");
        }
    }
}
