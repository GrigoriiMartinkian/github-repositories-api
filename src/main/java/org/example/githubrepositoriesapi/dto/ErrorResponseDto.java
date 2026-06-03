package org.example.githubrepositoriesapi.dto;

public record ErrorResponseDto(
        int status,
        String message
) {
}