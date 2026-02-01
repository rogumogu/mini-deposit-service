package com.rog.mini_deposit_service.service;

import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.entity.Deposit;

import java.time.LocalDateTime;

public interface DepositCommandService {
    Deposit save(Deposit deposit);
    Deposit create(String transactionId, String idempotencyKey, String channel, String serviceType, DepositRequest request, LocalDateTime timestamp);
}
