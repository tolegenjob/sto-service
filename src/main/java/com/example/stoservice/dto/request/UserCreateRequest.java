package com.example.stoservice.dto.request;

import com.example.stoservice.enums.UserRole;

public record UserCreateRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        UserRole role
) {
}
