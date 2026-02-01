package com.rog.deposit.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.topics.deposit-success}")
    private String depositSuccessTopic;

    @Bean
    public NewTopic checkDepositResultTopic() {
        return TopicBuilder.name(depositSuccessTopic).build();
    }
}
