package com.example.stoservice.dto.response;

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
) {
}
