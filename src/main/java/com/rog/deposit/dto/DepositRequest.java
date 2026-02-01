package com.rog.deposit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private Account debtorAccount;
    private Account creditorAccount;
    private BigDecimal amount;
    private String currency;
    private Map<String, Object> additionalData;
    private Notification debtorNotification;
    private Notification creditorNotification;
}
