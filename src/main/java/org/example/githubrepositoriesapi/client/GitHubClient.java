package org.example.githubrepositoriesapi.client;

import org.example.githubrepositoriesapi.dto.GitBranchDto;
import org.example.githubrepositoriesapi.dto.GitHubRepositoryDto;

import java.util.List;

public interface GitHubClient {

    List<GitHubRepositoryDto> getUserRepositories(String username);

    List<GitBranchDto> getBranches(String owner, String repo);
}
