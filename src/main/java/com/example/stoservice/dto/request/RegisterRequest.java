package com.example.stoservice.dto.request;

public record RegisterRequest(

        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber

) {
}
