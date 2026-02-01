package com.rog.deposit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rog.deposit.client.CoreBankingClient;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.dto.cbs.CbsResponse;
import com.rog.deposit.dto.cbs.DepositCbsRequest;
import com.rog.deposit.dto.cbs.WithdrawalCbsRequest;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.event.DepositSuccessEvent;
import com.rog.deposit.service.DepositCommandService;
import com.rog.deposit.event.producer.DepositEventPublisher;
import com.rog.deposit.service.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Slf4j
public abstract class BaseDepositService implements DepositService {

    protected DepositCommandService depositCommandService;
    protected ObjectMapper objectMapper;
    protected CoreBankingClient coreBankingService;
    protected DepositEventPublisher depositEventProducer;

    @Override
    public DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();

        Deposit deposit = depositCommandService.create(transactionId, idempotencyKey, channel, channel, request, timestamp);

        return processTransaction(deposit, request);
    }

    protected abstract DepositResponse processTransaction(Deposit deposit, DepositRequest request);

    protected CbsResponse cbsWithdrawTransaction(Deposit deposit, DepositRequest request) {
        WithdrawalCbsRequest withdrawalRequest = WithdrawalCbsRequest.builder()
                .accountNumber(request.getDebtorAccount().getAccountId())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .transactionId(deposit.getTransactionId())
                .build();

        CbsResponse withdrawalResponse = coreBankingService.withdraw(withdrawalRequest);
        log.info("Withdrawal completed from account: {}, status: {}", 
                request.getDebtorAccount().getAccountId(), withdrawalResponse.getStatus());
        
        return withdrawalResponse;
    }

    protected CbsResponse cbsDepositTransaction(Deposit deposit, DepositRequest request) {
        DepositCbsRequest depositCbsRequest = DepositCbsRequest.builder()
                .accountNumber(request.getCreditorAccount().getAccountId())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .transactionId(deposit.getTransactionId())
                .build();

        CbsResponse depositResponse = coreBankingService.deposit(depositCbsRequest);
        log.info("Deposit completed to account: {}, status: {}", 
                request.getCreditorAccount().getAccountId(), depositResponse.getStatus());
        
        return depositResponse;
    }

    protected void publishDepositSuccessEvent(Deposit deposit, DepositRequest request) {
        DepositSuccessEvent event = DepositSuccessEvent.builder()
                .transactionId(deposit.getTransactionId())
                .idempotencyKey(deposit.getIdempotencyKey())
                .channel(deposit.getChannel())
                .debtorAccount(request.getDebtorAccount())
                .creditorAccount(request.getCreditorAccount())
                .amount(deposit.getAmount())
                .currency(deposit.getCurrency())
                .status(deposit.getStatus())
                .additionalData(request.getAdditionalData())
                .debtorNotification(request.getDebtorNotification())
                .creditorNotification(request.getCreditorNotification())
                .build();
        
        depositEventProducer.publishDepositSuccess(event);
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setDepositCommandService(DepositCommandService depositCommandService) {
        this.depositCommandService = depositCommandService;
    }

    @Autowired
    public void setCoreBankingService(CoreBankingClient coreBankingService) {
        this.coreBankingService = coreBankingService;
    }

    @Autowired
    public void setDepositEventProducer(DepositEventPublisher depositEventProducer) {
        this.depositEventProducer = depositEventProducer;
    }

}
