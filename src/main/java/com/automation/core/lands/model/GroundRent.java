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
@Table(name = "ground_rent")
public class GroundRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String applicantName;
    private String email;
    private String formUrl;
}
