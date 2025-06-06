package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Vehicle;

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
) { public static VehicleResponse toDto(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getLicensePlate(),
                vehicle.getYear(),
                vehicle.getOwner().getId(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}
