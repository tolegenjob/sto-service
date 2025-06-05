package com.example.stoservice.dto.response;

import java.time.LocalDateTime;

public record VehicleUpdateResponse(
        Long id,
        String color,
        Long ownerId,
        LocalDateTime updatedAt
) {
}
