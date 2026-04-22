package com.erp.controller.material.request;

import com.erp.entity.enums.MaterialStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record MaterialFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        MaterialStatus status

) {
}
