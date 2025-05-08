package com.automation.core.lands.model;

import com.automation.util.enums.GlobalStatus;
import com.automation.util.enums.LandApplicationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "lands")
public class LandApplication {
    @Id
    @GeneratedValue
    private Long id;
    private String applicantName;
    private String email;
    private LandApplicationType applicationType;
    private GlobalStatus status;
    private LocalDateTime applicationDate;
    private LocalDateTime approvalDate;
    private String documents;
    private String certificateUrl;

}
