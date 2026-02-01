package com.rog.deposit.service;

import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.entity.Deposit;

import java.time.LocalDateTime;

public interface DepositCommandService {
    Deposit save(Deposit deposit);
    Deposit create(String transactionId, String idempotencyKey, String channel, String serviceType, DepositRequest request, LocalDateTime timestamp);
}
