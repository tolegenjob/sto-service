package com.example.stoservice.dto.request;

public record RequestCreateRequest(

        String title,
        String description,
        Long vehicleId

) {
}
