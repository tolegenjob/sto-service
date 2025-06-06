package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Vehicle;

import java.time.LocalDateTime;

public record VehicleUpdateResponse(
        Long id,
        String color,
        Long ownerId,
        LocalDateTime updatedAt
) { public static VehicleUpdateResponse toDto(Vehicle vehicle) {
        return new VehicleUpdateResponse(
                vehicle.getId(),
                vehicle.getColor(),
                vehicle.getOwner().getId(),
                vehicle.getUpdatedAt()
        );
    }
}
