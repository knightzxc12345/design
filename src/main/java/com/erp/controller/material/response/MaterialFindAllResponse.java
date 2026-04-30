package com.erp.controller.material.response;

import com.erp.entity.enums.MaterialStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record MaterialFindAllResponse(

        @Schema(description = "產品Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "供應商Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID supplierUuid,

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "代碼", example = "test")
        String code,

        @Schema(description = "規格", example = "test")
        String spec,

        @Schema(description = "單位", example = "100")
        String unit,

        @Schema(description = "成本", example = "100")
        BigDecimal cost,

        @Schema(description = "狀態", example = "ENABLE")
        MaterialStatus status

) {
}
