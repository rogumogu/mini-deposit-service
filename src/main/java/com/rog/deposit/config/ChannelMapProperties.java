package com.rog.deposit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;

@Configuration
@ConfigurationProperties("channel-properties")
@PropertySource(value = "classpath:channel-properties.yaml", factory = YamlPropertySourceFactory.class)
public class ChannelMapProperties extends HashMap<String, ChannelProperties> {

}
