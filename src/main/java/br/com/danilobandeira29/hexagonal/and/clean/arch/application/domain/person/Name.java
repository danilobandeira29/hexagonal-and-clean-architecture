package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

public record Name(String value) {
    public Name {
        if (value == null || value.isEmpty()) {
            throw new ValidationException("Invalid value for Name");
        }
    }
}
