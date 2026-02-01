package com.rog.deposit.client;

import com.rog.deposit.dto.pesonet.PesonetRequest;
import com.rog.deposit.dto.pesonet.PesonetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@ConditionalOnProperty(name = "pesonet.mock.enabled", havingValue = "true")
public class MockPesonetClient implements PesonetClient {

    @Override
    public PesonetResponse sendTransaction(PesonetRequest request) {
        log.info("MOCK PESONET: Sending transaction {} from {} to {} amount: {} {}", 
                request.getTransactionId(),
                request.getDebtorAccount(),
                request.getCreditorAccount(),
                request.getAmount(),
                request.getCurrency());
        
        return PesonetResponse.builder()
                .transactionId(request.getTransactionId())
                .referenceNumber("PN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .message("Mock PESONet transaction successful")
                .build();
    }
}
