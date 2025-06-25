package com.automation.core.commerce.dto.request;

import com.automation.core.commerce.model.BusinessType;
import com.automation.util.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRegistrationRequest {
    private String businessName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
    private Status status;
    private LocalDate dateRegistered;
    private boolean isRenewal;
    private String comment;
    private String businessNumber;
    private String paymentStatus;
//    private Long businessTypeId;

}
