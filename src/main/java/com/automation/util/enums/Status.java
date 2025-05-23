package com.automation.util.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum Status {
    PENDING(0), UNDER_REVIEW(2), APPROVED(1), REJECTED(2), CANCELLED(3);

    private final Integer value;

    Status(final Integer newValue) {
        value = newValue;
    }

    private static Optional<Status> valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(status -> Objects.equals(status.value, value))
                .findFirst();
    }

    public Integer getValue() {
        return value;
    }
}
