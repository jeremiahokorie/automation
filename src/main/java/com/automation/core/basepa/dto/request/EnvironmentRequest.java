package com.automation.core.basepa.dto.request;

import com.automation.util.enums.PermitType;
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
public class EnvironmentRequest {

    private String applicantName;
    private String contactPerson;
    private String phone;
    private String email;
    private String address;

    // Permit Details
    private PermitType permitType;
    private String wasteDescription;
    private String wasteSource;
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
//    private Integer durationInMonths;
//    private LocalDate preferredInspectionDate;
    private Status status;

    // File references (could be URLs or file names in a storage system)
//    private String businessRegistrationDoc;
//    private String wasteManagementPlan;
//    private String eiaReport;
//    private String previousPermitDoc;
//    private String taxClearanceCert;
}
