package com.example.stoservice.dto.response;

import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record RequestUpdateResponse(
        Long id,
        String title,
        String description,
        RequestStatus status,
        Long mechanicId,
        LocalDateTime updatedAt
) {
}
