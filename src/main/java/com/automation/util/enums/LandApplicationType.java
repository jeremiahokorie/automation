package com.automation.util.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LandApplicationType {
    COFO(1),
    GROUND_RENT(2),
    STATUTORY_ALLOCATION(3);

    private final int code;

    LandApplicationType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static LandApplicationType fromCode(int code) {
        return Arrays.stream(values())
                .filter(t -> t.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid application type"));
    }
}
