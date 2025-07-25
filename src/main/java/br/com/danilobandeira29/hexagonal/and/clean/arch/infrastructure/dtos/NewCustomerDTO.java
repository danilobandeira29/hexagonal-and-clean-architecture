package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos;


public record NewCustomerDTO(
        String name,
        String cpf,
        String email
) { }
