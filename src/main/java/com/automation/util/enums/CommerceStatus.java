package com.automation.util.enums;

import lombok.Getter;

public enum CommerceStatus {
    SUCCESS(0),
    PENDING(1),
    UNAUTHORIZED(2),
    FORBIDDEN(3),
    INVALID_REQUEST(4),
    NOT_FOUND(5),
    SYSTEM_ERROR(6),
    EXPECTATION_FAILED(7),
    PENDING_REGISTRAR_APPROVAL (8),
    APPROVED_BY_REGISTRAR(9),
    REJECTED(10);

    private Integer code;

    public Integer code() {
        return this.code;
    }

    private CommerceStatus(Integer code) {
        this.code = code;
    }

}
