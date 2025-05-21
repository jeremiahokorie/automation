package com.automation.core.commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String status;
    private LocalDate dateRegistered;
    private boolean isRenewal;
    private boolean isExpired;
    private String businessNumber;
    private String comment;
    private LocalDate renewalDate;

//    @ManyToOne
//    private BusinessType businessType;
}
