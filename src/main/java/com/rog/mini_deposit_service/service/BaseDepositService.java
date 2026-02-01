package com.rog.mini_deposit_service.service;

import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.dto.DepositResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public abstract class BaseDepositService implements DepositService {

    @Override
    public DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request) {
        log.info("Processing {} transaction: {}", getServiceType(), transactionId);

        DepositResponse response = DepositResponse.builder()
                .transactionId(transactionId)
                .idempotencyKey(idempotencyKey)
                .channel(channel)
                .debtorAccount(request.getDebtorAccount())
                .creditorAccount(request.getCreditorAccount())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .timestamp(LocalDateTime.now())
                .additionalData(request.getAdditionalData())
                .build();

        log.info("{} transaction processed successfully, transactionId={}", getServiceType(), transactionId);
        return response;
    }

    protected abstract String getServiceType();
}
