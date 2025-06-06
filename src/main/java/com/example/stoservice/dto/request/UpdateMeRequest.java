package com.example.stoservice.dto.request;

public record UpdateMeRequest(
        String password,
        String phoneNumber
) {
}
