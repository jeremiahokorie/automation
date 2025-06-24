package com.automation.util.integration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class InfoBipFeignConfiguration {
    @Value("${integrations.info-bip.api-key:0987654321}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("Authorization", apiKey);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new StashErrorDecoder();
//    }
}
