package com.example.stoservice.dto.response;

import java.time.LocalDateTime;

public record VehicleResponse(
        Long id,
        String brand,
        String model,
        String color,
        String licensePlate,
        int year,
        Long ownerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
