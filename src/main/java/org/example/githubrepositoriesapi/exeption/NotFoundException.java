package org.example.githubrepositoriesapi.exeption;

public final class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}