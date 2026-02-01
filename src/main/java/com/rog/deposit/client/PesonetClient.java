package com.rog.deposit.client;

import com.rog.deposit.dto.pesonet.PesonetRequest;
import com.rog.deposit.dto.pesonet.PesonetResponse;

public interface PesonetClient {
    PesonetResponse sendTransaction(PesonetRequest request);
}
