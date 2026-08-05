package com.automation.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "credo")
@Component
@Data
public class CredoProperties {
    private String paymentGatewayUrl;
    private String paymentGatewayPublicKey;
    
}
