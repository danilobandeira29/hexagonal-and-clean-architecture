package br.com.danilobandeira29.infrastructure.dtos;

public record NewEventDTO(
        String name,
        String date,
        int totalSpots,
        String partnerId
) { }
