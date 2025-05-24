package com.automation.core.payment.controller;

import com.automation.core.payment.dto.request.PaymentRequest;
import com.automation.core.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initialize")
    public ResponseEntity<String> initializePayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.initializePayment(paymentRequest);
    }
}
