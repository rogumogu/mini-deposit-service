package com.rog.deposit.service.impl;

import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.event.DepositSuccessEvent;
import com.rog.deposit.service.IntrabankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IntrabankServiceImpl extends BaseDepositService implements IntrabankService {

    @Override
    protected DepositResponse processTransaction(Deposit deposit, DepositRequest request) {
        log.info("Processing Intrabank transaction: {}", deposit.getTransactionId());
        
        cbsWithdrawTransaction(deposit, request);
        cbsDepositTransaction(deposit, request);
        
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
        
        log.info("Intrabank transaction processed successfully, transactionId={}", deposit.getTransactionId());
        
        publishDepositSuccessEvent(deposit, request);
        
        return response;
    }

    public void sendNotification(DepositSuccessEvent event) {
        log.info("INTRABANK: Sending notifications for transactionId: {}", event.getTransactionId());
    }
}
