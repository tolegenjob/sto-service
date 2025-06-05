package com.example.stoservice.dto.response;

import com.example.stoservice.enums.UserRole;

import java.time.LocalDateTime;

public record UserCreateResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime createdAt
) {
}
