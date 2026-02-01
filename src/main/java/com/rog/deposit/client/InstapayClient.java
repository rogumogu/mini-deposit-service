package com.rog.deposit.client;

import com.rog.deposit.dto.instapay.InstapayRequest;
import com.rog.deposit.dto.instapay.InstapayResponse;

public interface InstapayClient {
    InstapayResponse sendTransaction(InstapayRequest request);
}
