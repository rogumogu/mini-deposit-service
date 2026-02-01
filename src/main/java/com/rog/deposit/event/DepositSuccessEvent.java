package com.rog.deposit.event;

import com.rog.deposit.dto.Account;
import com.rog.deposit.dto.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositSuccessEvent {
    private String transactionId;
    private String idempotencyKey;
    private String channel;
    private Account debtorAccount;
    private Account creditorAccount;
    private BigDecimal amount;
    private String currency;
    private String status;
    private Map<String, Object> additionalData;
    private Notification debtorNotification;
    private Notification creditorNotification;
}
