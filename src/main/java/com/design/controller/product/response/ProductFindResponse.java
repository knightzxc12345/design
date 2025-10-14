package com.design.controller.product.response;

import com.design.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record ProductFindResponse(

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "代碼", example = "test")
        String code,

        @Schema(description = "描述", example = "test")
        String description,

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

        @Schema(description = "品項 UUID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        List<ProductItem> items,

        @Schema(description = "產品狀態", example = "ACTIVE")
        ProductStatus status

) {
}
