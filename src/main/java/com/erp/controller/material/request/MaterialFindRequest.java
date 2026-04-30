package com.erp.controller.material.request;

import com.erp.entity.enums.MaterialStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record MaterialFindRequest(

        @Schema(description = "供應商Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "供應商uuid不得為空")
        UUID supplierUuid,

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        MaterialStatus status

) {
}
