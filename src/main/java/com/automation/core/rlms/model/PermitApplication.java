package com.automation.core.rlms.model;

import com.automation.util.enums.PermitType;
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
public class PermitApplication {
    @Id
    @GeneratedValue
    private Long id;

    private String applicantName;
    private String email;

    @Enumerated(EnumType.STRING)
    private PermitType permitType;

    private String documentUrl;
    private String status;
    private String licenseUrl;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
}
