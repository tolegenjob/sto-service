package com.example.stoservice.dto.request;

import com.example.stoservice.enums.RequestStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestUpdateRequest(

        @NotNull(message = "Status must be provided")
        RequestStatus status,

        @Size(max = 500, message = "Reason must be at most 500 characters")
        String reason

) {
}
