package com.rog.mini_deposit_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.entity.Deposit;
import com.rog.mini_deposit_service.repository.DepositRepository;
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
