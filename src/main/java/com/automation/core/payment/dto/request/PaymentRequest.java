package com.automation.core.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private int amount;
    private int bearer;
    private String callbackUrl;
    private List<String> channels;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhoneNumber;
    private String email;
}
