package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos;

public record SubscribeDTO (
        String customerId,
        String eventId
){ }
