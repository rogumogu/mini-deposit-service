package com.rog.mini_deposit_service.orchestrator;

import com.rog.mini_deposit_service.config.ChannelMapProperties;
import com.rog.mini_deposit_service.config.ChannelProperties;
import com.rog.mini_deposit_service.dto.DepositRequest;
import com.rog.mini_deposit_service.dto.DepositResponse;
import com.rog.mini_deposit_service.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelOrchestrator {

    private final ChannelMapProperties channelMapProperties;
    private final Map<String, DepositService> depositServiceMap;

    private String getService(String channel) {
        ChannelProperties channelProperties = channelMapProperties.get(channel);
        return channelProperties.getService();
    }

    public DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request) {
        log.info("Creating deposit for channel: {}", channel);

        String serviceName = getService(channel);
        if (serviceName == null) {
            log.error("No service found for channel: {}", channel);
            throw new IllegalArgumentException("Unsupported channel: " + channel);
        }

        DepositService depositService = depositServiceMap.get(serviceName);
        return depositService.createDeposit(transactionId, idempotencyKey, channel, request);
    }
}
