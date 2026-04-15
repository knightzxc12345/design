package com.erp.controller.brand.request;

import com.erp.entity.enums.BrandStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record BrandFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        BrandStatus status

) {
}
