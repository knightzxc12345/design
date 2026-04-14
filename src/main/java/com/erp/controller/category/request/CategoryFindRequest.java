package com.erp.controller.category.request;

import com.erp.entity.enums.CategoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        CategoryStatus status

) {
}
