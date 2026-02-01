package com.rog.mini_deposit_service.service;

import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.dto.DepositResponse;

public interface DepositService {
    DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request);
}
