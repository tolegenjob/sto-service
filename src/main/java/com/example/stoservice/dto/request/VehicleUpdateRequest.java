package com.example.stoservice.dto.request;

public record VehicleUpdateRequest(
        Long id,
        String color,
        Long ownerId
) {
}
