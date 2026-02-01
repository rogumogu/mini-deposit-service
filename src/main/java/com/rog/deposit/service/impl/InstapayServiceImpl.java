package com.rog.deposit.service.impl;

import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.service.InstapayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstapayServiceImpl extends BaseDepositService implements InstapayService {

    @Override
    protected DepositResponse processTransaction(Deposit deposit, DepositRequest request) {
        log.info("Processing InstaPay transaction: {}", deposit.getTransactionId());
        
        DepositResponse response = DepositResponse.builder()
                .transactionId(deposit.getTransactionId())
                .idempotencyKey(deposit.getIdempotencyKey())
                .channel(deposit.getChannel())
                .debtorAccount(request.getDebtorAccount())
                .creditorAccount(request.getCreditorAccount())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .status(deposit.getStatus())
                .timestamp(deposit.getTimestamp())
                .additionalData(request.getAdditionalData())
                .build();
        
        log.info("InstaPay transaction processed successfully, transactionId={}", deposit.getTransactionId());
        return response;
    }
}
