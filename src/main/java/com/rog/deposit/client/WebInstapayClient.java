package com.rog.deposit.client;

import com.rog.deposit.dto.instapay.InstapayRequest;
import com.rog.deposit.dto.instapay.InstapayResponse;
import com.rog.deposit.exception.DepositProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "instapay.mock.enabled", havingValue = "false", matchIfMissing = true)
public class WebInstapayClient implements InstapayClient {

    @Value("${instapay.base-url}")
    private String instapayBaseUrl;

    private final WebClient.Builder webClientBuilder;

    @Override
    public InstapayResponse sendTransaction(InstapayRequest request) {
        log.info("Calling InstaPay API for transaction: {}", request.getTransactionId());

        try {
            WebClient webClient = webClientBuilder.baseUrl(instapayBaseUrl).build();
            
            InstapayResponse response = webClient.post()
                    .uri("/api/v1/transfer")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(InstapayResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            log.info("InstaPay transaction successful: {}", request.getTransactionId());
            return response;
        } catch (Exception ex) {
            log.error("InstaPay transaction failed: {}", request.getTransactionId(), ex);
            throw new DepositProcessingException("Failed to send InstaPay transaction: " + request.getTransactionId(), ex);
        }
    }
}
