package com.rog.deposit.orchestrator;

import com.rog.deposit.config.ChannelMapProperties;
import com.rog.deposit.config.ChannelProperties;
import com.rog.deposit.dto.DepositRequest;
import com.rog.deposit.dto.DepositResponse;
import com.rog.deposit.exception.InvalidChannelException;
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
        if (!channelMapProperties.containsKey(channel)) {
            throw new InvalidChannelException(channel);
        }
        ChannelProperties channelProperties = channelMapProperties.get(channel);
        return channelProperties.getService();
    }

    public DepositResponse createDeposit(String transactionId, String idempotencyKey, String channel, DepositRequest request) {
        log.info("Creating deposit for channel: {}", channel);

        String serviceName = getService(channel);

        DepositService depositService = depositServiceMap.get(serviceName);
        return depositService.createDeposit(transactionId, idempotencyKey, channel, request);
    }
}
