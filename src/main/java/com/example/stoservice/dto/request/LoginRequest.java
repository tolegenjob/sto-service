package com.example.stoservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email should be valid")
        @Size(min = 4, max = 50, message = "Email must be between 4 and 20 symbols")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 100, message = "Password must be at least 8 symbols")
        String password

) {
}
