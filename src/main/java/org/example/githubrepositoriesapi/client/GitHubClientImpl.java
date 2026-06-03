package org.example.githubrepositoriesapi.client;

import org.example.githubrepositoriesapi.dto.GitBranchDto;
import org.example.githubrepositoriesapi.dto.GitHubRepositoryDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.util.List;

@Component
public final class GitHubClientImpl implements GitHubClient {

    private final RestClient restClient;

    public GitHubClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<GitHubRepositoryDto> getUserRepositories(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<GitHubRepositoryDto>>() {});
    }

    public List<GitBranchDto> getBranches(String owner, String repo) {
        return restClient.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .body(new org.springframework.core.ParameterizedTypeReference<List<GitBranchDto>>() {});
    }
}