package com.rog.mini_deposit_service.service;

import org.springframework.stereotype.Service;

@Service("IntrabankService")
public class IntrabankService extends BaseDepositService {

    @Override
    protected String getServiceType() {
        return "Intrabank";
    }
}
