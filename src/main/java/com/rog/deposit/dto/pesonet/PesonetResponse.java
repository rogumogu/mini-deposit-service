package com.rog.deposit.dto.pesonet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PesonetResponse {
    private String transactionId;
    private String referenceNumber;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime timestamp;
    private String message;
}
