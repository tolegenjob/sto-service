package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Vehicle;

import java.time.LocalDateTime;

public record VehicleCreateResponse(
        Long id,
        String brand,
        String model,
        String color,
        String licensePlate,
        int year,
        Long ownerId,
        LocalDateTime createdAt
) { public static VehicleCreateResponse toDto(Vehicle vehicle) {
        return new VehicleCreateResponse(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getColor(),
                vehicle.getLicensePlate(),
                vehicle.getYear(),
                vehicle.getOwner().getId(),
                vehicle.getCreatedAt()
        );
    }
}
