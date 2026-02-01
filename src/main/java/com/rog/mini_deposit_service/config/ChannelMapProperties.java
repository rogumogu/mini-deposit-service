package com.rog.mini_deposit_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties("channel-properties")
public class ChannelMapProperties extends HashMap<String, ChannelProperties> {

}
