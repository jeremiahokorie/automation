package com.automation.core.basepa.model;


import com.automation.util.enums.PermitType;
import com.automation.util.enums.SourceOfWaste;
import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "environment")
public class EnvironmentApplication {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        // Applicant Info
        private String applicantName;
        private String contactPerson;
        private String phone;
        private String email;
        private String address;
        private Boolean isPayed;

        // Permit Details
        private PermitType permitType;
        private String wasteDescription;
        private SourceOfWaste wasteSource;
        private Double wasteQuantity;
        private String disposalFrequency;
        private String disposalMethod;
        private String disposalLocation;

        // Facility Info
        private String facilityName;
        private String facilityAddress;
        private String industryType;
        private String operationalLicenseNumber;
        private Boolean hasEnvironmentalAudit;

        // Metadata
        private LocalDate applicationDate;
//        private Integer durationInMonths;
//        private LocalDate preferredInspectionDate;
//        @Enumerated(EnumType.STRING)
        private Status status;
        private String comment;
        private LocalDate approvalDate;
        private LocalDate rejectionDate;

        // File references (could be URLs or file names in a storage system)
//        private String businessRegistrationDoc;
//        private String wasteManagementPlan;
//        private String eiaReport;
//        private String previousPermitDoc;
//        private String taxClearanceCert;

        // Constructors, Getters, Setters
    }



