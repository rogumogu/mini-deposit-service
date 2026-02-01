package com.rog.mini_deposit_service.service;

import org.springframework.stereotype.Service;

@Service("InstapayService")
public class InstapayService extends BaseDepositService {

    @Override
    protected String getServiceType() {
        return "InstaPay";
    }
}
