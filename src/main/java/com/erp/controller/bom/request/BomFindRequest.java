package com.erp.controller.bom.request;

import com.erp.entity.enums.BomStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record BomFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        BomStatus status

) {
}
