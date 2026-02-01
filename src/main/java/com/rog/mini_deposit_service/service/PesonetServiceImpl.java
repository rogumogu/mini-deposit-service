package com.rog.mini_deposit_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PesonetServiceImpl extends BaseDepositService implements PesonetService {

    @Override
    protected String getServiceType() {
        return "PESONet";
    }
}
