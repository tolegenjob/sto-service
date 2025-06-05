package com.example.stoservice.dto.response;

import com.example.stoservice.enums.UserRole;

import java.time.LocalDateTime;

public record UserUpdateResponse(
        Long id,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime updatedAt
) {
}
