# GitHub Repositories API

Simple REST API that provides information about GitHub user repositories.  
The service works as a proxy to GitHub public API and returns only non-fork repositories with branch details.

---

## Tech stack

- Java 25
- Spring Boot 4
- Spring Web (RestClient)
- JUnit 5
- WireMock (for integration tests)
- JSONAssert

---

## Endpoint

### Get user repositories
GET /api/v1/github/{username}/repos
---

## What it returns

For a given GitHub user, API returns list of repositories that are NOT forks.

Each repository contains:

- repository name
- owner login
- list of branches

Each branch contains:

- branch name
- last commit SHA

---

## Example response

```json
[
  {
    "repositoryName": "repo1",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "1a2b3c"
      }
    ]
  }
]
```

---

## Error response

If user does not exist:
HTTP 404
```json
{
  "status": 404,
  "message": "GitHub user not found"
}
```

---

## External API

Data is fetched from GitHub API:  
https://developer.github.com/v3

---

## Tests

Integration tests are written with:

- SpringBootTest (random port)
- WireMock (mock GitHub API)
- JSONAssert (JSON validation)

Covered cases:

- user with repositories (mix fork and non-fork)
- user not found
- empty repository list


---

## Run
./gradlew bootRun