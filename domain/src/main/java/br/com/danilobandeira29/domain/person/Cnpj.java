package br.com.danilobandeira29.domain.person;

import br.com.danilobandeira29.domain.exceptions.ValidationException;

public record Cnpj(String value) {

    public Cnpj {
        if (value == null || !value.matches("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$")) {
            throw new ValidationException("Invalid value for Cnpj");
        }
    }
}
