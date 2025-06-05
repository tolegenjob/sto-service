package com.example.stoservice.dto.event;

import com.example.stoservice.dto.request.RequestUpdateRequest;
import com.example.stoservice.enums.RequestStatus;

public record StatusEvent(
        RequestStatus fromStatus,
        RequestUpdateRequest updateRequest,
        Long changedById
) {
}
