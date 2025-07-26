package br.com.danilobandeira29.domain.person;

import br.com.danilobandeira29.domain.exceptions.ValidationException;

public record Name(String value) {
    public Name {
        if (value == null || value.isEmpty()) {
            throw new ValidationException("Invalid value for Name");
        }
    }
}
