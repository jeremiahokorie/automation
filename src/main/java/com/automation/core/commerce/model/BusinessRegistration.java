package com.automation.core.commerce.model;

import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BusinessRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String ownerName;
    private String address;
    private String phone;
    private String email;
//  @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate dateRegistered;
    private boolean isRenewal;
    private boolean isExpired;
    private String businessNumber;
    private String comment;
    private LocalDate renewalDate;
    private LocalDateTime createdAt;
    private String AuthorizationUrl;
    private String paymentStatus;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

//    @ManyToOne
//    private BusinessType businessType;
}
