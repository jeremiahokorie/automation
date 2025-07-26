package com.automation.core.inspection.model;


import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.global.model.User;
import com.automation.core.lands.model.CustomaryAllocationApplication;
import com.automation.core.lands.model.StatutoryAllocationApplication;
import com.automation.util.enums.Status;
import io.swagger.annotations.ApiParam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID requestId;
    private String sourceService;
    private String applicantName;
    private String applicationType;
    private Status status;

    private String assignedTo;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String paymentStatus;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // Direct relationship with BusinessRegistration
    @OneToOne
    @JoinColumn(name = "business_registration_id")
    private BusinessRegistration businessRegistration;

    @OneToOne
    @JoinColumn(name = "statutory_allocation_applications_id")
    private StatutoryAllocationApplication statutoryAllocationApplication;

    @OneToOne
    @JoinColumn(name = "customary_allocation_applications_id")
    private CustomaryAllocationApplication customaryAllocationApplication;

    @OneToOne()
    @JoinColumn(name = "environment_id")
    @ApiParam()
    private EnvironmentApplication environment;


}
