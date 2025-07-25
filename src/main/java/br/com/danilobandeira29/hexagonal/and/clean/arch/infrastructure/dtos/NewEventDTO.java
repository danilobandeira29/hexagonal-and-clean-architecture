package br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.dtos;

public record NewEventDTO(
        String name,
        String date,
        int totalSpots,
        String partnerId
) { }
