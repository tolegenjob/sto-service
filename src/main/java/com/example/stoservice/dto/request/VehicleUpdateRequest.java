package com.example.stoservice.dto.request;

import jakarta.validation.constraints.Size;

public record VehicleUpdateRequest(

        @Size(max = 30, message = "Color must be at most 30 characters")
        String color
) {
}
