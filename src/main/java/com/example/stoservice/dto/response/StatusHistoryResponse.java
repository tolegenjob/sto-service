package com.example.stoservice.dto.response;

import com.example.stoservice.enums.RequestStatus;

public record StatusHistoryResponse(
        Long id,
        Long requestId,
        RequestStatus fromStatus,
        RequestStatus toStatus,
        String reason,
        Long changedById
) {
}
