package com.automation.core.lands.model;

import com.automation.util.enums.Status;
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
@Table(name = "ground_rent")
public class GroundRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String baNo;
    private String landNo;
    private String record;
    private Double rent;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String optionalFile;
    private LocalDateTime createdAt = LocalDateTime.now();
}
