package com.rog.mini_deposit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResponse {
    private String transactionId;
    private String idempotencyKey;
    private String channel;
    private Account debtorAccount;
    private Account creditorAccount;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime timestamp;
    private Map<String, Object> additionalData;
}
