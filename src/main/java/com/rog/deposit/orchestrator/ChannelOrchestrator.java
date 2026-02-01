package com.rog.deposit.orchestrator;

import com.rog.deposit.config.ChannelMapProperties;
import com.rog.deposit.config.ChannelProperties;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
