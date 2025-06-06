package com.example.stoservice.dto.response;

import com.example.stoservice.entity.User;

import java.time.LocalDateTime;

public record RegisterResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDateTime createdAt
) { public static RegisterResponse toDto(User user) {
        return new RegisterResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt());
    }
}
