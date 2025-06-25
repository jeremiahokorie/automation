package com.automation.core.inspection.model;


import com.automation.core.basepa.model.EnvironmentApplication;
import com.automation.core.commerce.model.BusinessRegistration;
import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
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

//    // Direct relationship with User
//    @ManyToOne
//    @JoinColumn(name = "user_id")  // Foreign key column in the Inspection table
//    private User user;

    // Direct relationship with BusinessRegistration
    @OneToOne
    @JoinColumn(name = "business_registration_id")
    private BusinessRegistration businessRegistration;

    // Direct relationship with Environment
    @OneToOne()
    @JoinColumn(name = "environment_application_id")
    private EnvironmentApplication environment;


}
