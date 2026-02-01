package com.rog.mini_deposit_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntrabankServiceImpl extends BaseDepositService implements IntrabankService {

    @Override
    protected String getServiceType() {
        return "Intrabank";
    }
}
