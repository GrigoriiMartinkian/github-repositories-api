package org.example.githubrepositoriesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(@Value("${github.api.url}") String url) {
        return RestClient.builder()
                .baseUrl(url)
                .build();
    }
}
