package org.example.githubrepositoriesapi.dto;

public record BranchResponseDto(
        String name,
        String lastCommitSha
) {
}
