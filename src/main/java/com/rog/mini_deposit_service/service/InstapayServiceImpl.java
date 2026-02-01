package com.rog.mini_deposit_service.service;

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
