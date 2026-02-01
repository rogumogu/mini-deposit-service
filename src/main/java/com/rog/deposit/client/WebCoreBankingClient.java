package com.rog.deposit.client;

import com.rog.deposit.dto.cbs.CbsResponse;
import com.rog.deposit.dto.cbs.DepositCbsRequest;
import com.rog.deposit.dto.cbs.WithdrawalCbsRequest;
import com.rog.deposit.exception.DepositProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cbs.mock.enabled", havingValue = "false", matchIfMissing = true)
public class WebCoreBankingClient implements CoreBankingClient {

    private final WebClient cbsWebClient;

    @Override

    public CbsResponse withdraw(WithdrawalCbsRequest request) {
        log.info("Calling CBS withdrawal API for account: {}, amount: {}", 
                request.getAccountNumber(), request.getAmount());

        try {
            CbsResponse response = cbsWebClient.post()
                    .uri("/api/v1/accounts/withdraw")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CbsResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            log.info("CBS withdrawal successful for transactionId: {}", request.getTransactionId());
            return response;
        } catch (Exception ex) {
            log.error("CBS withdrawal failed for transactionId: {}", request.getTransactionId(), ex);
            throw new DepositProcessingException("Failed to withdraw from account: " + request.getAccountNumber(), ex);
        }
    }

    @Override
    public CbsResponse deposit(DepositCbsRequest request) {
        log.info("Calling CBS deposit API for account: {}, amount: {}", 
                request.getAccountNumber(), request.getAmount());

        try {
            CbsResponse response = cbsWebClient.post()
                    .uri("/api/v1/accounts/deposit")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(CbsResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            log.info("CBS deposit successful for transactionId: {}", request.getTransactionId());
            return response;
        } catch (Exception ex) {
            log.error("CBS deposit failed for transactionId: {}", request.getTransactionId(), ex);
            throw new DepositProcessingException("Failed to deposit to account: " + request.getAccountNumber(), ex);
        }
    }
}
