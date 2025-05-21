package com.automation.core.lands.model;

import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statutory_allocations")
public class StatutoryAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicantName;
    private String applicantEmail;
    private String passportPhotos;
    private String taxClearances;
    private String declarationOfAge;
    private String administrativeCharges;
    private String processingFees;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}