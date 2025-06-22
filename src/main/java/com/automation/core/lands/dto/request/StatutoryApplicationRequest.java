package com.automation.core.lands.dto.request;

import com.automation.util.enums.Status;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatutoryApplicationRequest {
    private LocalDate applicationDate;

    private String applicationNo;

    private String luacNo;

    private String title; // Mr, Mrs, Alhaji, etc.

    private String titleOther;

    @NotBlank
    private String applicantName;

    private String nationality;

    private String nationalityOther;

    private String placeOfBirth;

    private String stateOfOrigin;

    private LocalDate dateOfBirth;

    private String gender;

    private String maritalStatus;

    private String occupation;

    private String occupationOther;

    private String homeAddress;

    private String townOrArea;

    private String lga;

    private String phoneNumber;

    private String email;

    private Boolean ownsStateLand;

    private String existingTitleNo;

    private String existingLandLocation;

    private Boolean isLandDeveloped;

    private Boolean isAssignorOrAssignee;

    private String assignedTitleNo;

    private String assignedLandLocation;

    private LocalDate assignedDate;

    private String assigneeNameAndAddress;

    private Boolean assignedLandDeveloped;

    private String plotType; // Residential, Commercial, etc.

    private String plotTypeDetail; // Hotel, School, etc.

    private String residentialBuildingType; // Block of flats, etc.

    private String plotSize;

    private String plotSizeOther;

    private String applicationFeeType;

    private BigDecimal applicationFeeAmount;

    private String otherFeesBreakdown;

    private BigDecimal proposedInvestment;

    private String investmentFinancing;

    private Boolean previousGovtAcquisition;

    private String previousAcquisitionLocation;

    private String previousAcquisitionSize;

    private LocalDate previousAcquisitionDate;

    private String acquiringAuthority;

    private String compensationStatus;

    private String compensationPartPayment;

    @Lob
    private String swornDeclaration;

    private String applicantSignature;

    private LocalDate declarationDate;

    private Boolean illiterateJuratAttached;

    // Attachments (these should be saved separately or as paths/filenames)
    private String passportPhoto;
    private String taxClearance;
    private String feeReceipt;
    private String ageDeclaration;
    private String naturalizationDoc;
    private String oathDeclaration;
    private Status status;

    private LocalDate dateOfApplication;
    private MultipartFile passportOfOwner;

    private String applicantTitle;
    private String nameOfApplicant;
    private String otherNationality; // if "Others" is selected
    private String telephoneNumber;
    private String assigneeOrAssignorNameAndAddress;
    private String plotActivityDetail; // For Commercial or Educational plot types
    private String applicationFeeCategory;
    private String developmentFinanceSource;
    private boolean propertyPreviouslyAcquiredByGovernment;
    private String acquisitionDetails;
    private String location;
    private LocalDate dateOfAcquisition;

//    // Corporate section (optional)
//    private String incorporationCertificatePath;
//    private String memorandumArticlesPath;
//    private String directorPhotoPath;
//    private String corpTaxClearance1Path;
//    private String corpTaxClearance2Path;
//    private String corpTaxClearance3Path;
//
//    // Incorporated Trustees (optional)
//    private String regDoc1Path;
//    private String regDoc2Path;
//    private String regDoc3Path;
//    private String otherDoc1Path;
//    private String otherDoc2Path;
//    private String otherDoc3Path;
//    private String otherDoc4Path;

    // GPS info
//    private Double latitude;
//    private Double longitude;
//    private Double altitude;
//    private Double gpsAccuracy;
}
