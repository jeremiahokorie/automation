package com.automation.core.abiaid.model;

import com.automation.core.global.model.User;
import com.automation.util.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "abia_state_identification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbiaStateIdentification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "abia_id_number", nullable = false, unique = true)
    private String abiaIdNumber;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(name = "state_of_origin")
    private String stateOfOrigin;

    @Column(name = "lga", nullable = false)
    private String lga;

    @Column(name = "ward", nullable = false)
    private String ward;

    @Column(name = "nin", nullable = false, unique = true)
    private String nin;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "date_applied")
    private LocalDate dateApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
