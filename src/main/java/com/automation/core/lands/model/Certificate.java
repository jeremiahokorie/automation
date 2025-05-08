package com.automation.core.lands.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String applicantName;
    private String email;
    private String districtHeadLetterUrl;
    private String salesAgreementUrl;
    private String declarationOfAgeUrl;
    private String taxClearanceUrl;
    private String surveyDataUrl;
    private String localGovernmentConfirmationUrl;
    private String formUrl;
}
