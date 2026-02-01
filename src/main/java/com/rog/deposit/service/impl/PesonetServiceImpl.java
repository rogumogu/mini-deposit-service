package com.rog.deposit.service.impl;

import com.rog.deposit.service.PesonetService;
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
