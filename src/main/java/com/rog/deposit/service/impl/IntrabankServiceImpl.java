package com.rog.deposit.service.impl;

import com.rog.deposit.service.IntrabankService;
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
