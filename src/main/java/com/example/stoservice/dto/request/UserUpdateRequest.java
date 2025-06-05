package com.example.stoservice.dto.request;

import com.example.stoservice.enums.UserRole;

public record UserUpdateRequest(
        String password,
        String phoneNumber,
        UserRole role
) {
}
