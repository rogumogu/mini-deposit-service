package com.rog.mini_deposit_service.service;

import org.springframework.stereotype.Service;

@Service("PesonetService")
public class PesonetService extends BaseDepositService {

    @Override
    protected String getServiceType() {
        return "PESONet";
    }
}
