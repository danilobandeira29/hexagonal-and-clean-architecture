package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new ValidationException("Invalid value for Email");
        }
    }
}
