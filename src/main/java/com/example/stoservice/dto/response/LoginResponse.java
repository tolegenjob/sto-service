package com.example.stoservice.dto.response;

public record LoginResponse(
        String email,
        String token
) {
}
