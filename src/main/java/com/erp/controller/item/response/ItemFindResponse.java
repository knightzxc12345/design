package com.erp.controller.item.response;

import com.erp.entity.enums.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemFindResponse(

        @Schema(description = "產品Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "產品Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID productUuid,

        @Schema(description = "sku code", example = "test")
        String skuCode,

        @Schema(description = "規格", example = "test")
        String spec,

        @Schema(description = "價格", example = "100")
        BigDecimal price,

        @Schema(description = "成本", example = "100")
        BigDecimal cost,

        @Schema(description = "狀態", example = "ENABLE")
        ItemStatus status

) {
}
