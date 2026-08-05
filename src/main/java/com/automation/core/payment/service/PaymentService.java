package com.automation.core.payment.service;

import com.automation.config.CredoProperties;
import com.automation.core.payment.dto.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final CredoProperties credoProperties;

    private final RestTemplate restTemplate;
    private final String PAYMENT_GATEWAY_URL = "https://api.credodemo.com/transaction/initialize";
    private final String GATEWAY_PUBLIC_KEY = "0PUB1332AtGMSf3QmqCmxBYvRVq24R6h";

    public ResponseEntity<String> initializePayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", GATEWAY_PUBLIC_KEY);

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);
        return restTemplate.exchange(PAYMENT_GATEWAY_URL, HttpMethod.POST, requestEntity, String.class);
    }

}