package com.example.stoservice.dto.response;

import com.example.stoservice.entity.User;
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
) { public static UserCreateResponse toDto(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
