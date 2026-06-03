package org.example.githubrepositoriesapi.dto;

import java.util.List;

public record RepositoryResponseDto(
        String repositoryName,
        String ownerLogin,
        List<BranchResponseDto> branches
) {
}