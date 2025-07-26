package br.com.danilobandeira29.infrastructure.dtos;


public record NewCustomerDTO(
        String name,
        String cpf,
        String email
) { }
