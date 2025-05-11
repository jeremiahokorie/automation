package com.automation.core.global.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Column(name = "PERMISSION_ID")
    private Long permissionId;
    @Column(name = "ROLE_ID")
    private Long roleId;
}