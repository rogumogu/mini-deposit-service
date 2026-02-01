package com.rog.deposit.client;

import com.rog.deposit.dto.cbs.CbsResponse;
import com.rog.deposit.dto.cbs.DepositCbsRequest;
import com.rog.deposit.dto.cbs.WithdrawalCbsRequest;

public interface CoreBankingService {
    CbsResponse withdraw(WithdrawalCbsRequest request);
    CbsResponse deposit(DepositCbsRequest request);
}
