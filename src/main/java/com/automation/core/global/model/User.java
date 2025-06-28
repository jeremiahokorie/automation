package com.automation.core.global.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "createDate")
    private LocalDate createDate;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String nin;
    private String status;
    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry_datetime")
    private LocalDateTime resetTokenExpiryDateTime;


    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "user_lock")
    private Integer userLock;

    @Column(name = "user_lock_date")
    private Date userLockDate;

    @Column(name = "last_password_reset_date")
    private Date lastPasswordResetDate;
    private Boolean isPayed = false;

    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;

    @ManyToOne
    private Roles role;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;


    public List<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().flatMap(r -> r.getAuthorities().stream())
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
//
//
//    @Override
//    public String getUsername() {
//        return "";
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
}
