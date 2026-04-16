package com.erp.controller.product.request;

import com.erp.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProductFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        ProductStatus status

) {
}
