package com.example.stoservice.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse (
        HttpStatus status,
        String message,
        String error,
        LocalDateTime timestamp ) {
}
