package com.erp.controller.brand.response;

import com.erp.entity.enums.BrandStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record BrandFindAllResponse(

        @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "代碼", example = "test")
        String code,

        @Schema(description = "說明", example = "88888888")
        String description,

        @Schema(description = "狀態", example = "ENABLE")
        BrandStatus status

) {
}
