package com.rog.deposit.dto.pesonet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PesonetRequest {
    private String transactionId;
    private String debtorAccount;
    private String creditorAccount;
    private BigDecimal amount;
    private String currency;
}
