package com.rog.deposit.client;

import com.rog.deposit.dto.cbs.CbsResponse;
import com.rog.deposit.dto.cbs.DepositCbsRequest;
import com.rog.deposit.dto.cbs.WithdrawalCbsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@ConditionalOnProperty(name = "cbs.mock.enabled", havingValue = "true")
public class MockCoreBankingService implements CoreBankingService {

    @Override
    public CbsResponse withdraw(WithdrawalCbsRequest request) {
        log.info("MOCK CBS: Withdrawing {} {} from account: {}", 
                request.getAmount(), request.getCurrency(), request.getAccountNumber());
        
        return CbsResponse.builder()
                .transactionId(request.getTransactionId())
                .accountNumber(request.getAccountNumber())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .message("Mock withdrawal successful")
                .build();
    }

    @Override
    public CbsResponse deposit(DepositCbsRequest request) {
        log.info("MOCK CBS: Depositing {} {} to account: {}", 
                request.getAmount(), request.getCurrency(), request.getAccountNumber());
        
        return CbsResponse.builder()
                .transactionId(request.getTransactionId())
                .accountNumber(request.getAccountNumber())
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .message("Mock deposit successful")
                .build();
    }
}
