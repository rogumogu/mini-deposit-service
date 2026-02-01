package com.rog.deposit.client;

import com.rog.deposit.dto.pesonet.PesonetRequest;
import com.rog.deposit.dto.pesonet.PesonetResponse;
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
@ConditionalOnProperty(name = "pesonet.mock.enabled", havingValue = "false", matchIfMissing = true)
public class WebPesonetClient implements PesonetClient {

    @Value("${pesonet.base-url}")
    private String pesonetBaseUrl;

    private final WebClient.Builder webClientBuilder;

    @Override
    public PesonetResponse sendTransaction(PesonetRequest request) {
        log.info("Calling PESONet API for transaction: {}", request.getTransactionId());

        try {
            WebClient webClient = webClientBuilder.baseUrl(pesonetBaseUrl).build();
            
            PesonetResponse response = webClient.post()
                    .uri("/api/v1/transfer")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PesonetResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            log.info("PESONet transaction successful: {}", request.getTransactionId());
            return response;
        } catch (Exception ex) {
            log.error("PESONet transaction failed: {}", request.getTransactionId(), ex);
            throw new DepositProcessingException("Failed to send PESONet transaction: " + request.getTransactionId(), ex);
        }
    }
}
