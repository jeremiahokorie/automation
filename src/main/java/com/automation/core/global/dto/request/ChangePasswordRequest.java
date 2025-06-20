package com.automation.core.global.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public record ChangePasswordRequest(@NotBlank String currentPassword,
                                    @NotBlank @Size(min = 8) String newPassword) {
}
