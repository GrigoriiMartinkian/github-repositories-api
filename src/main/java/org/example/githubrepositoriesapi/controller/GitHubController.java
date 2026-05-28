package org.example.githubrepositoriesapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.example.githubrepositories.dto.RepositoryResponseDto;
import org.example.githubrepositories.service.GitHubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public final class GitHubController {

    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/{username}/repos")
    public List<RepositoryResponseDto> getRepositories(@PathVariable String username) {
        return service.getUserRepositories(username);
    }
}