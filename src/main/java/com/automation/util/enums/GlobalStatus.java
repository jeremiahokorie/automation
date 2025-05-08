package com.automation.util.enums;

import lombok.Getter;

@Getter
public enum GlobalStatus {

    SUCCESS(0),
    PENDING(1),
    UNAUTHORIZED(2),
    FORBIDDEN(3),
    INVALID_REQUEST(4),
    NOT_FOUND(5),
    SYSTEM_ERROR(6),
    EXPECTATION_FAILED(7);

    private Integer code;

    public Integer code() {
        return this.code;
    }

    private GlobalStatus(Integer code) {
        this.code = code;
    }
}
