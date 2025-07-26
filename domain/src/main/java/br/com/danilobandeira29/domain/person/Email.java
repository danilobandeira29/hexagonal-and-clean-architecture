package br.com.danilobandeira29.domain.person;

import br.com.danilobandeira29.domain.exceptions.ValidationException;

public record Email(String value) {

    public Email {
        if (value == null || !value.matches("^\\S+@\\S+\\.\\S+$")) {
            throw new ValidationException("Invalid value for Email");
        }
    }
}
