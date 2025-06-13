package com.automation.core.lands.dto.request;

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
public class CustomaryAllocationRequest {
        public Long id;
        public LocalDate applicationDate;
        public String applicantName;
        public String applicantTitle;
        public String nationality;
        public String placeOfBirth;
        public String stateOfOrigin;
        public LocalDate dateOfBirth;
        public String gender;
        public String maritalStatus;
        public String occupation;
        public String homeAddress;
        public String townOrArea;
        public String lga;
        public String phoneNumber;
        public String email;
        public Boolean ownsCustomaryLand;
        public String existingLandLocation;
        public String landPurpose;
        public String purposeDetail;
        public String proposedBuildingType;
        public String plotSize;
        public String plotSizeOther;
        public BigDecimal applicationFeeAmount;
        public BigDecimal proposedDevelopmentCost;
        public String developmentFinancingPlan;
        public Boolean communityConsent;
        public LocalDate communityConsentDate;
        public String communityLeaderName;
        public String communityLeaderTitle;
        public String signatureOrMark;
        public LocalDate declarationDate;
        public Double latitude;
        public Double longitude;
        public Double altitude;
        public Double gpsAccuracy;

        public String passportPhoto;
        public String taxClearance;
        public String affidavit;
        public String communityConsentLetter;
        public String developmentSketch;
    }
