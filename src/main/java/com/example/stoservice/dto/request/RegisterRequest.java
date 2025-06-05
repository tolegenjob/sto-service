package com.example.stoservice.dto.request;

import com.example.stoservice.enums.UserRole;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber
) {
}
