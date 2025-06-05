package com.example.stoservice.dto.request;

import com.example.stoservice.enums.RequestStatus;

public record RequestUpdateRequest(

        Long id,
        RequestStatus status,
        String reason,
        Long mechanicId

) {
}
