package com.example.stoservice.dto.response;

import com.example.stoservice.entity.StatusHistory;
import com.example.stoservice.enums.RequestStatus;

import java.time.LocalDateTime;

public record StatusHistoryResponse(
        Long id,
        Long requestId,
        RequestStatus fromStatus,
        RequestStatus toStatus,
        String reason,
        Long changedById,
        LocalDateTime timestamp
) { public static StatusHistoryResponse toDto(StatusHistory statusHistory) {
        return new StatusHistoryResponse(
                statusHistory.getId(),
                statusHistory.getRequest().getId(),
                statusHistory.getFromStatus(),
                statusHistory.getToStatus(),
                statusHistory.getReason(),
                statusHistory.getChangedById(),
                statusHistory.getCreatedAt()
        );
    }
}
