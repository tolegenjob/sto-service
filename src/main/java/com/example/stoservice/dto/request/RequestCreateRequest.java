package com.example.stoservice.dto.request;

import com.example.stoservice.enums.RequestStatus;

public record RequestCreateRequest(

        String title,
        String description,
        RequestStatus status,
        Long clientId,
        Long vehicleId

) {
}
