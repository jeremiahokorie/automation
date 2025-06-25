package com.automation.core.lands.dto.request;

import com.automation.util.enums.Status;
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
        private LocalDate applicationDate;
        private String applicantName;
        private String firstName;
        private String lastName;
        private String applicantTitle;
        private String nationality;
        private String placeOfBirth;
        private String stateOfOrigin;
        private LocalDate dateOfBirth;
        private String gender;
        private String maritalStatus;
        private String occupation;
        private String homeAddress;
        private String townOrArea;
        private String lga;
        private String phoneNumber;
        private String email;
        private Boolean ownsCustomaryLand;
        private String existingLandLocation;
        private String landPurpose;
        private String purposeDetail;
        private String proposedBuildingType;
        private String plotSize;
        private String plotSizeOther;
        private String employerName;
        private String officeAddress;
        private BigDecimal applicationFeeAmount;
        private BigDecimal proposedDevelopmentCost;
        private String developmentFinancingPlan;
        private Boolean communityConsent;
        private LocalDate communityConsentDate;
        private String communityLeaderName;
        private String communityLeaderTitle;
        private String signatureOrMark;
        private LocalDate declarationDate;
        private String companyName;
        private String companyType;
        private String contactAddress;
        private String companyPhoneNumber;
        private String phone;
        private String cacRegistrationNo;
        private LocalDate registrationDate; // date-month-year
        private String sourceOfCapital;
        private String managerName;
        private String ceoName;
        private String contactPerson;
        private String representativeName;
        private String representativeAddress;
        private String representativePhone;
        private String representativeEmail;
        private Status status;

        private String plotLocation;
        private String landAcquisitionMode; // e.g. purchase, LGA allocation, inheritance, others
        private String landUsePurpose;
        private String sheetNo;
        private String areaOfficeNo;
        private String areaSize;
        private String paymentStatus;

//        public Double latitude;
//        public Double longitude;
//        public Double altitude;
//        public Double gpsAccuracy;

        private String passportPhoto;
        private String taxClearance;
        private String affidavit;
        private String communityConsentLetter;
        private String developmentSketch;

    }
