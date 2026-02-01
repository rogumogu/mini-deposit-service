package com.rog.deposit.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.entity.Deposit;
import com.rog.deposit.repository.DepositRepository;
import com.rog.deposit.service.DepositCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepositCommandServiceImpl implements DepositCommandService {

    private final DepositRepository depositRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    @Transactional
    public Deposit create(String transactionId, String idempotencyKey, String channel, String serviceType, DepositRequest request, LocalDateTime timestamp) {
        Deposit deposit = Deposit.builder()
                .transactionId(transactionId)
                .idempotencyKey(idempotencyKey)
                .channel(channel)
                .serviceType(serviceType)
                .debtorAccount(objectMapper.convertValue(request.getDebtorAccount(), Map.class))
                .creditorAccount(objectMapper.convertValue(request.getCreditorAccount(), Map.class))
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .additionalData(request.getAdditionalData())
                .timestamp(timestamp)
                .build();

        return depositRepository.save(deposit);
    }
}
