package com.rog.deposit.service;

import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.event.DepositSuccessEvent;

public interface DepositService {
    DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request);
    void sendNotification(DepositSuccessEvent event);
}
