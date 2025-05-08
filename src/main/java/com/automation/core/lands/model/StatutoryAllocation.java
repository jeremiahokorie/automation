package com.automation.core.lands.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statutory_allocation")
public class StatutoryAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String applicantName;
    private String email;
    private String taxClearanceUrl;
    private String declarationOfAgeUrl;
    private String formUrl;
}
