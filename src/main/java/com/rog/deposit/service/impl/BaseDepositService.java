package com.rog.deposit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.service.DepositCommandService;
import com.rog.deposit.service.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public abstract class BaseDepositService implements DepositService {

    protected DepositCommandService depositCommandService;
    protected ObjectMapper objectMapper;

    @Override
    public DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request) {
        log.info("Processing {} transaction: {}", getServiceType(), transactionId);

        LocalDateTime timestamp = LocalDateTime.now();

        depositCommandService.create(transactionId, idempotencyKey, channel, getServiceType(), request, timestamp);

        DepositResponse response = DepositResponse.builder()
                .transactionId(transactionId)
                .idempotencyKey(idempotencyKey)
                .channel(channel)
                .debtorAccount(request.getDebtorAccount())
                .creditorAccount(request.getCreditorAccount())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .timestamp(timestamp)
                .additionalData(request.getAdditionalData())
                .build();

        log.info("{} transaction processed successfully, transactionId={}", getServiceType(), transactionId);
        return response;
    }

    protected abstract String getServiceType();

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setDepositCommandService(DepositCommandService depositCommandService) {
        this.depositCommandService = depositCommandService;
    }

}
