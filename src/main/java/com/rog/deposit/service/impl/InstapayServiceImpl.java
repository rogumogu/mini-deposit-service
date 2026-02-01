package com.rog.deposit.service.impl;

import com.rog.deposit.client.InstapayClient;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.dto.instapay.InstapayRequest;
import com.rog.deposit.dto.instapay.InstapayResponse;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.event.DepositSuccessEvent;
import com.rog.deposit.service.InstapayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstapayServiceImpl extends BaseDepositService implements InstapayService {

    private final InstapayClient instapayClient;

    @Override
    protected DepositResponse processTransaction(Deposit deposit, DepositRequest request) {
        log.info("Processing InstaPay transaction: {}", deposit.getTransactionId());
        
        cbsWithdrawTransaction(deposit, request);
        sendInstapayTransaction(deposit, request);
        
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
        
        publishDepositSuccessEvent(deposit, request);
        
        return response;
    }

    private InstapayResponse sendInstapayTransaction(Deposit deposit, DepositRequest request) {
        InstapayRequest instapayRequest = InstapayRequest.builder()
                .transactionId(deposit.getTransactionId())
                .debtorAccount(request.getDebtorAccount().getAccountId())
                .creditorAccount(request.getCreditorAccount().getAccountId())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .build();

        InstapayResponse instapayResponse = instapayClient.sendTransaction(instapayRequest);
        log.info("InstaPay transaction sent, referenceNumber: {}, status: {}", 
                instapayResponse.getReferenceNumber(), instapayResponse.getStatus());
        
        return instapayResponse;
    }

    public void sendNotification(DepositSuccessEvent event) {
        log.info("INSTAPAY: Sending notifications for transactionId: {}", event.getTransactionId());
    }
}
