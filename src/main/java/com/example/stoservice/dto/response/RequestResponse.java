package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Request;
import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record RequestResponse(
        Long id,
        String title,
        String description,
        RequestStatus currentStatus,
        Long clientId,
        Long mechanicId,
        Long vehicleId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) { public static RequestResponse toDto(Request request) {
    return new RequestResponse(
            request.getId(),
            request.getTitle(),
            request.getDescription(),
            request.getCurrentStatus(),
            request.getClient().getId(),
            request.getMechanic() != null ? request.getMechanic().getId() : null,
            request.getVehicle().getId(),
            request.getCreatedAt(),
            request.getUpdatedAt()
    );
}
}
