package com.example.stoservice.dto.request;

import com.example.stoservice.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(

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
        String phoneNumber,

        @NotNull(message = "User role must be provided")
        UserRole role
) {
}
