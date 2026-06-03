package org.example.githubrepositoriesapi;


import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

class GitHubControllerIT extends AbstractIT {

    private final RestClient client = RestClient.create();

    @Test
    void shouldReturnOnlyNonForkRepositories() {

        wireMockServer.stubFor(
                get(urlEqualTo("/users/test/repos"))
                        .willReturn(okJson("""
                                [
                                  {
                                    "name": "repo1",
                                    "fork": false,
                                    "owner": { "login": "test" }
                                  },
                                  {
                                    "name": "repo2",
                                    "fork": true,
                                    "owner": { "login": "test" }
                                  }
                                ]
                                """))
        );

        wireMockServer.stubFor(
                get(urlEqualTo("/repos/test/repo1/branches"))
                        .willReturn(okJson("""
                                [
                                  {
                                    "name": "main",
                                    "commit": { "sha": "111" }
                                  }
                                ]
                                """))
        );

        String url = baseUrl() + "/api/v1/github/test/repos";

        String response = client.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        String expected = """
                [
                  {
                    "repositoryName": "repo1",
                    "ownerLogin": "test",
                    "branches": [
                      {
                        "name": "main",
                        "lastCommitSha": "111"
                      }
                    ]
                  }
                ]
                """;

        assertDoesNotThrow(() ->
                JSONAssert.assertEquals(expected, response, false)
        );
    }

    @Test
    void shouldReturn404WhenUserNotFound() {

        wireMockServer.stubFor(
                get(urlEqualTo("/users/unknown/repos"))
                        .willReturn(notFound())
        );

        String url = baseUrl() + "/api/v1/github/unknown/repos";

        assertThrows(HttpClientErrorException.NotFound.class, () ->
                client.get()
                        .uri(url)
                        .retrieve()
                        .body(String.class)
        );
    }

    @Test
    void shouldReturnEmptyList() {

        wireMockServer.stubFor(
                get(urlEqualTo("/users/empty/repos"))
                        .willReturn(okJson("[]"))
        );

        String url = baseUrl() + "/api/v1/github/empty/repos";

        String response = client.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        assertDoesNotThrow(() ->
                JSONAssert.assertEquals("[]", response, false)
        );
    }

    @Test
    void shouldReturnEmptyListWhenNoRepositories() {

        wireMockServer.stubFor(
                get(urlEqualTo("/users/test/repos"))
                        .willReturn(okJson("[]"))
        );

        wireMockServer.stubFor(
                get(urlEqualTo("/repos/test/repo1/branches"))
                        .willReturn(okJson("[]"))
        );

        String url = baseUrl() + "/api/v1/github/test/repos";

        String response = client.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        assertDoesNotThrow(() ->
                JSONAssert.assertEquals("[]", response, false)
        );
    }
}