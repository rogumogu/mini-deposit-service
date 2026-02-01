package com.rog.deposit.service.impl;

import com.rog.deposit.client.PesonetClient;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.dto.pesonet.PesonetRequest;
import com.rog.deposit.dto.pesonet.PesonetResponse;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.event.DepositSuccessEvent;
import com.rog.deposit.service.PesonetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PesonetServiceImpl extends BaseDepositService implements PesonetService {

    private final PesonetClient pesonetClient;

    @Override
    protected DepositResponse processTransaction(Deposit deposit, DepositRequest request) {
        log.info("Processing PESONet transaction: {}", deposit.getTransactionId());
        
        cbsWithdrawTransaction(deposit, request);
        sendPesonetTransaction(deposit, request);
        
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
        
        log.info("PESONet transaction processed successfully, transactionId={}", deposit.getTransactionId());
        
        publishDepositSuccessEvent(deposit, request);
        
        return response;
    }

    private PesonetResponse sendPesonetTransaction(Deposit deposit, DepositRequest request) {
        PesonetRequest pesonetRequest = PesonetRequest.builder()
                .transactionId(deposit.getTransactionId())
                .debtorAccount(request.getDebtorAccount().getAccountId())
                .creditorAccount(request.getCreditorAccount().getAccountId())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .build();

        PesonetResponse pesonetResponse = pesonetClient.sendTransaction(pesonetRequest);
        log.info("PESONet transaction sent, referenceNumber: {}, status: {}", 
                pesonetResponse.getReferenceNumber(), pesonetResponse.getStatus());
        
        return pesonetResponse;
    }

    public void sendNotification(DepositSuccessEvent event) {
        log.info("PESONET: Sending notifications for transactionId: {}", event.getTransactionId());
    }
}
