package com.rog.deposit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "error-code")
@PropertySource(value = "classpath:error-codes.yaml", factory = YamlPropertySourceFactory.class)
public class ErrorCodeProperties extends HashMap<String, ErrorResponse> {

}
