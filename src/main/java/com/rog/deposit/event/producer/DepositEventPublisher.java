package com.rog.deposit.event.producer;

import com.rog.deposit.event.DepositSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositEventPublisher {

    private final KafkaTemplate<String, DepositSuccessEvent> kafkaTemplate;
    
    @Value("${spring.kafka.topics.deposit-success}")
    private String depositSuccessTopic;

    public void publishDepositSuccess(DepositSuccessEvent event) {
        log.info("Publishing deposit success event for transactionId: {}", event.getTransactionId());
        kafkaTemplate.send(depositSuccessTopic, event.getTransactionId(), event);
    }
}
