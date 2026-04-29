package com.automation.util.enums;

public enum RegistrationStatus {
    ACTIVE("Active"),
    EXPIRED("Expired"),
    RENEWED("Renewed"),
    SUSPENDED("Suspended"),
    CANCELLED("Cancelled");

    private final String displayName;

    RegistrationStatus(String displayName) {
        this.displayName = displayName;
    }
}
