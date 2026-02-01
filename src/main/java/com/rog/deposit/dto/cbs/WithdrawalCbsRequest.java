package com.rog.deposit.dto.cbs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalCbsRequest {
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private String transactionId;
}
