package com.example.abac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpaConfig {

    @Value("${opa.url}")
    private String opaUrl;

    @Bean
    public WebClient opaWebClient() {
        return WebClient.builder()
                .baseUrl(opaUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}