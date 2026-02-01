package com.rog.deposit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "cbs.mock.enabled", havingValue = "false", matchIfMissing = true)
public class WebClientConfig {

    @Value("${cbs.base-url}")
    private String cbsBaseUrl;

    @Bean
    public WebClient cbsWebClient() {
        log.info("Initializing CBS WebClient with base URL: {}", cbsBaseUrl);
        return WebClient.builder()
                .baseUrl(cbsBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
