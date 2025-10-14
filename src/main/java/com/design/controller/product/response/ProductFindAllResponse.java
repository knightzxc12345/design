package com.design.controller.product.response;

import com.design.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "代碼", example = "test")
        String code,

        @Schema(description = "尺寸", example = "15吋")
        String dimension,

        @Schema(description = "單位", example = "台")
        String unit,

        @Schema(description = "成本金額", example = "35000")
        BigDecimal costPrice,

        @Schema(description = "報價金額", example = "35000")
        BigDecimal price,

        @Schema(description = "圖片 URL", example = "https://example.com/image.png")
        String imageUrl,

        @Schema(description = "產品狀態", example = "ACTIVE")
        ProductStatus status

) {
}
