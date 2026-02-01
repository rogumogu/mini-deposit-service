package com.rog.mini_deposit_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String description;
}
