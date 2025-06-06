package com.example.stoservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VehicleCreateRequest(

        @NotBlank(message = "Brand must not be blank")
        @Size(max = 50, message = "Brand must be at most 50 characters")
        String brand,

        @NotBlank(message = "Model must not be blank")
        @Size(max = 50, message = "Model must be at most 50 characters")
        String model,

        @NotBlank(message = "Color must not be blank")
        @Size(max = 30, message = "Color must be at most 30 characters")
        String color,

        @NotBlank(message = "License plate must not be blank")
        @Size(max = 15, message = "License plate must be at most 15 characters")
        String licensePlate,

        @Min(value = 1900, message = "Year must be no earlier than 1900")
        @Max(value = 2025, message = "Year must be no later than 2025")
        int year
) {
}
