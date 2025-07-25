package br.com.danilobandeira29.hexagonal.and.clean.arch.application.domain.person;

import br.com.danilobandeira29.hexagonal.and.clean.arch.application.exceptions.ValidationException;

public record Cpf(String value) {

    public Cpf {
        if (value == null || !value.matches("^\\d{3}\\.\\d{3}.\\d{3}-\\d{2}$")) {
            throw new ValidationException("Invalid value for Cpf");
        }
    }
}
