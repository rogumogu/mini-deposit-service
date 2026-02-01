package com.rog.deposit.event.consumer;

import com.rog.deposit.config.ChannelMapProperties;
import com.rog.deposit.config.ChannelProperties;
import com.rog.deposit.event.DepositSuccessEvent;
import com.rog.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DepositEventConsumer {

    private final ChannelMapProperties channelMapProperties;
    private final Map<String, DepositService> depositServiceMap;

    @KafkaListener(topics = "deposit-success-events", groupId = "deposit-notification-group")
    public void consumeDepositSuccessEvent(DepositSuccessEvent event) {
        log.info("Consumed deposit success event for transactionId: {}, channel: {}", 
                event.getTransactionId(), event.getChannel());
        
        try {
            String serviceName = getService(event.getChannel());
            DepositService depositService = depositServiceMap.get(serviceName);
            
            depositService.sendNotification(event);
            
            log.info("Notifications sent successfully for transactionId: {}", event.getTransactionId());
        } catch (Exception ex) {
            log.error("Failed to send notifications for transactionId: {}", event.getTransactionId(), ex);
        }
    }

    private String getService(String channel) {
        if (!channelMapProperties.containsKey(channel)) {
            log.warn("Unknown channel: {}", channel);
            throw new IllegalArgumentException("Unknown channel: " + channel);
        }
        ChannelProperties channelProperties = channelMapProperties.get(channel);
        return channelProperties.getService();
    }
}
