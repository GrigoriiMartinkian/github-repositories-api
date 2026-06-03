package org.example.githubrepositoriesapi.service;

import org.example.githubrepositoriesapi.client.GitHubClient;
import org.example.githubrepositoriesapi.dto.BranchResponseDto;
import org.example.githubrepositoriesapi.dto.GitHubRepositoryDto;
import org.example.githubrepositoriesapi.dto.RepositoryResponseDto;
import org.example.githubrepositoriesapi.exeption.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public final class GitHubService {

    private final GitHubClient client;

    public GitHubService(GitHubClient client) {
        this.client = client;
    }

    public List<RepositoryResponseDto> getUserRepositories(final String username) {

        final var repos = getRepositories(username);

        if (repos.isEmpty()) {
            return List.of();
        }

        return repos.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {

                    final var branches = client.getBranches(
                                    repo.owner().login(),
                                    repo.name()
                            ).stream()
                            .map(branch -> new BranchResponseDto(
                                    branch.name(),
                                    branch.commit().sha()
                            ))
                            .toList();

                    return new RepositoryResponseDto(
                            repo.name(),
                            repo.owner().login(),
                            branches
                    );
                })
                .toList();
    }

    private List<GitHubRepositoryDto> getRepositories(final String username) {

        try {
            return client.getUserRepositories(username);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("GitHub user not found");
        }
    }
}
