package com.example.stoservice.dto.request;

import com.example.stoservice.enums.RequestStatus;

public record RequestUpdateRequest(

        RequestStatus status,
        String reason

) {
}
