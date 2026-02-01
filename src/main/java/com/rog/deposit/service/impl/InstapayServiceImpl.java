package com.rog.deposit.service.impl;

import com.rog.deposit.service.InstapayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstapayServiceImpl extends BaseDepositService implements InstapayService {

    @Override
    protected String getServiceType() {
        return "InstaPay";
    }
}
