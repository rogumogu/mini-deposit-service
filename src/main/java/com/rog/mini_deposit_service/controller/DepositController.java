package com.rog.mini_deposit_service.controller;

import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.dto.DepositResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/deposits")
public class DepositController {

    @PostMapping
    public ResponseEntity<DepositResponse> createDeposit(@RequestBody DepositRequest request) {
        log.info("Received deposit request for account: {}, amount: {}", 
                request.getAccountId(), request.getAmount());

        DepositResponse response = DepositResponse.builder()
                .transactionId(UUID.randomUUID().toString())
                .accountId(request.getAccountId())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .timestamp(LocalDateTime.now())
                .message("Deposit request received successfully")
                .build();

        log.info("Created deposit transaction: {}", response.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<DepositResponse> getDepositStatus(@PathVariable String transactionId) {
        log.info("Fetching deposit status for transaction: {}", transactionId);

        DepositResponse response = DepositResponse.builder()
                .transactionId(transactionId)
                .status("PENDING")
                .timestamp(LocalDateTime.now())
                .message("Deposit transaction found")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Deposit service is running");
    }
}
