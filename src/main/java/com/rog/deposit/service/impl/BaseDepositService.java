package com.rog.deposit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.entity.Deposit;
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
        LocalDateTime timestamp = LocalDateTime.now();

        Deposit deposit = depositCommandService.create(transactionId, idempotencyKey, channel, channel, request, timestamp);

        return processTransaction(deposit, request);
    }

    protected abstract DepositResponse processTransaction(Deposit deposit, DepositRequest request);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setDepositCommandService(DepositCommandService depositCommandService) {
        this.depositCommandService = depositCommandService;
    }

}
