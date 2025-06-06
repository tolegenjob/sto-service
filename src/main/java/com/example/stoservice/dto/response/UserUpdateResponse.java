package com.example.stoservice.dto.response;

import com.example.stoservice.entity.User;
import com.example.stoservice.enums.UserRole;

import java.time.LocalDateTime;

public record UserUpdateResponse(
        Long id,
        String email,
        String phoneNumber,
        UserRole role,
        LocalDateTime updatedAt
) { public static UserUpdateResponse toDto(User user) {
    return new UserUpdateResponse(
            user.getId(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getRole(),
            user.getUpdatedAt()
    );
}
}
