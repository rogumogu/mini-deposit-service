package com.rog.mini_deposit_service.controller;

import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.dto.DepositResponse;
import com.rog.mini_deposit_service.orchestrator.ChannelOrchestrator;
import com.rog.mini_deposit_service.service.BaseDepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/deposits")
@RequiredArgsConstructor
public class DepositController {

    private final ChannelOrchestrator channelOrchestrator;

    @PostMapping
    public ResponseEntity<DepositResponse> createDeposit(
            @RequestHeader("channel") String channel,
            @RequestHeader("idempotency-Key") String idempotencyKey,
            @RequestHeader("transaction-id") String transactionId,
            @RequestHeader("request-source") String requestSource,
            @RequestBody DepositRequest request) {
        log.info("Received deposit request, channel={}, idempotencyKey={}, transactionId={}, requestSource={}, requestBody={}",
                channel, idempotencyKey, transactionId, requestSource, request);

        DepositResponse response = channelOrchestrator.createDeposit(transactionId, idempotencyKey, channel, request);

        log.info("Created deposit transaction, channel={}, responseBody={}", channel, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
