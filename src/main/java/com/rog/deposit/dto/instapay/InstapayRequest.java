package com.rog.deposit.dto.instapay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstapayRequest {
    private String transactionId;
    private String debtorAccount;
    private String creditorAccount;
    private BigDecimal amount;
    private String currency;
}
