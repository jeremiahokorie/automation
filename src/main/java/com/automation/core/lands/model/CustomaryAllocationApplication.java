package com.automation.core.lands.model;

import com.automation.util.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customary_allocation_applications")
public class CustomaryAllocationApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate applicationDate;

    @NotBlank
    private String applicantName;

    private String applicantTitle;

    private String nationality;

    private String placeOfBirth;

    private String stateOfOrigin;

    private LocalDate dateOfBirth;

    private String gender;
    private Status status;

    private String maritalStatus;

    private String occupation;

    private String homeAddress;

    private String townOrArea;

    private String lga;

    private String phoneNumber;

    private String email;

    private Boolean ownsCustomaryLand;

    private String existingLandLocation;

    private String landPurpose; // Residential, Commercial, etc.

    private String purposeDetail; // Hostel, School, etc.

    private String proposedBuildingType;

    private String plotSize;

    private String plotSizeOther;

    private BigDecimal applicationFeeAmount;

    private BigDecimal proposedDevelopmentCost;

    private String developmentFinancingPlan;

    private Boolean communityConsent;

    private LocalDate communityConsentDate;

    private String communityLeaderName;

    private String communityLeaderTitle;

    private String signatureOrMark;

    private LocalDate declarationDate;

    // Attachments
    private String passportPhoto;
    private String taxClearance;
    private String affidavit;
    private String communityConsentLetter;
    private String developmentSketch;

    // GPS (if collected)
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double gpsAccuracy;

    // Getters and Setters (or use Lombok)
}

