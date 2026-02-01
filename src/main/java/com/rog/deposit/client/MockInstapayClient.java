package com.rog.deposit.client;

import com.rog.deposit.dto.instapay.InstapayRequest;
import com.rog.deposit.dto.instapay.InstapayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@ConditionalOnProperty(name = "instapay.mock.enabled", havingValue = "true")
public class MockInstapayClient implements InstapayClient {

    @Override
    public InstapayResponse sendTransaction(InstapayRequest request) {
        log.info("MOCK INSTAPAY: Sending transaction {} from {} to {} amount: {} {}", 
                request.getTransactionId(),
                request.getDebtorAccount(),
                request.getCreditorAccount(),
                request.getAmount(),
                request.getCurrency());
        
        return InstapayResponse.builder()
                .transactionId(request.getTransactionId())
                .referenceNumber("IP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .message("Mock InstaPay transaction successful")
                .build();
    }
}
