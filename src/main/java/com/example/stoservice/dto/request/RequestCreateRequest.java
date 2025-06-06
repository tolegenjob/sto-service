package com.example.stoservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestCreateRequest(

        @NotBlank(message = "Title must not be blank")
        @Size(max = 100, message = "Title must be at most 100 characters")
        String title,


        @Size(max = 500, message = "Description must be at most 500 characters")
        String description,

        @NotNull(message = "Vehicle ID must be provided")
        Long vehicleId

) {
}
