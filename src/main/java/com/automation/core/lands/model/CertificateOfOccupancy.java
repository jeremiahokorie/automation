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
@Table(name = "certificate_of_occupancy")
public class CertificateOfOccupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicantName;
    private String districtHeadLetter;
    private String salesAgreement;
    private String declarationOfAge;
    private String taxClearance;
    private String surveyData;
    private String status;
    private String applicantEmail;
    private String localGovernmentConfirmationLetter;
    private LocalDateTime createdAt = LocalDateTime.now();
}
