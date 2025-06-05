package com.example.stoservice.dto.request;

public record VehicleCreateRequest(
        String brand,
        String model,
        String color,
        String licensePlate,
        int year,
        Long ownerId
) {
}
