package com.example.stoservice.dto.response;

import com.example.stoservice.entity.Request;
import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record RequestUpdateResponse(
        Long id,
        String title,
        String description,
        RequestStatus status,
        Long mechanicId,
        LocalDateTime updatedAt
) { public static RequestUpdateResponse toDto(Request request) {
        return  new RequestUpdateResponse(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getCurrentStatus(),
                request.getMechanic() != null ? request.getMechanic().getId() : null,
                request.getUpdatedAt()
        );
    }
}
