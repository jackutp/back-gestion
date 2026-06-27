// com.microservicio.incidentes.config.WebClientConfig.java
package com.microservicio.incidentes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
@Slf4j
public class WebClientConfig {

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.username}")
    private String username;

    @Value("${jira.token}")
    private String token;

    @Bean
    public WebClient webClient() {
        // Crear autenticación básica
        String auth = username + ":" + token;
        String encodedAuth = Base64.getEncoder()
                .encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        log.info("Configurando WebClient para Jira Service Management:");
        log.info("  URL: {}", jiraUrl);
        log.info("  Username: {}", username);
        log.info("  Auth: Basic {}", encodedAuth.substring(0, 20) + "...");

        return WebClient.builder()
                .baseUrl(jiraUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Atlassian-Token", "no-check")
                .filter(logRequest())
                .filter(logResponse())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                log.debug("=== JIRA REQUEST ===");
                log.debug("URI: {}", clientRequest.url());
                log.debug("Method: {}", clientRequest.method());
                log.debug("Headers: {}", clientRequest.headers());
            }
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            if (log.isDebugEnabled()) {
                log.debug("=== JIRA RESPONSE ===");
                log.debug("Status: {}", clientResponse.statusCode());
            }
            return Mono.just(clientResponse);
        });
    }
}