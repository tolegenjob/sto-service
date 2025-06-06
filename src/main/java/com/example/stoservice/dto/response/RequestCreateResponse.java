package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Request;
import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record RequestCreateResponse(
        Long id,
        String title,
        String description,
        RequestStatus currentStatus,
        Long clientId,
        Long vehicleId,
        LocalDateTime createdAt
) { public static RequestCreateResponse toDto(Request request) {
        return new RequestCreateResponse(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getCurrentStatus(),
                request.getClient().getId(),
                request.getVehicle().getId(),
                request.getCreatedAt()
        );
    }
}
