package org.example.githubrepositoriesapi.dto;

public record GitBranchDto(
        String name,
        CommitDto commit
) {
}
