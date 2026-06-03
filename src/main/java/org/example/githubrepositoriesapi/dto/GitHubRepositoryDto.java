package org.example.githubrepositoriesapi.dto;

public record GitHubRepositoryDto(
        String name,
        boolean fork,
        OwnerDto owner
) {
}
