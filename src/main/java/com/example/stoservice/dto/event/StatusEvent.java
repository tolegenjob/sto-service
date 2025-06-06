package com.example.stoservice.dto.event;

import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record StatusEvent(
        Long requestId,
        RequestStatus fromStatus,
        RequestStatus toStatus,
        String reason,
        Long changedById,
        LocalDateTime timestamp
) {
}
