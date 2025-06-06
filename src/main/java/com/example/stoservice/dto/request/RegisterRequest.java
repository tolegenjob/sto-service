package com.example.stoservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "First name must not be blank")
        @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        @Email(message = "Email should be valid")
        @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
        String lastName,

        @NotBlank(message = "Email must not be blank")
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 symbols")
        String email,

        @NotBlank(message = "Password must not be blank")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,100}$",
                message = "Password must be at least 8 symbols, including uppercase and lowercase letters, numbers and special characters [@$!%*?&]"
        )
        String password,

        @NotBlank(message = "Phone number must not be blank")
        @Pattern(
                regexp = "^\\+7(?:\\s*\\d){10}$",
                message = "Phone number must start with +7 и содержать ровно 10 цифр, между которыми допускаются любые пробелы"
        )
        String phoneNumber

) {
}
