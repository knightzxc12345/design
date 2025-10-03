package com.design.controller.item.response;

import com.design.entity.enums.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ItemFindResponse(

        @Schema(description = "品項名稱", example = "筆記型電腦")
        String name,

        @Schema(description = "品項代號", example = "LAPTOP001")
        String code,

        @Schema(description = "尺寸", example = "15吋")
        String dimension,

        @Schema(description = "描述", example = "高效能筆記型電腦")
        String description,

        @Schema(description = "圖片 URL", example = "https://example.com/image.png")
        String imageUrl,

        @Schema(description = "單位", example = "台")
        String unit,

        @Schema(description = "報價金額", example = "35000")
        BigDecimal price,

        @Schema(description = "供應商uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String supplierUuid,

        @Schema(description = "供應商名稱", example = "宏碁")
        String supplierName,

        @Schema(description = "品項狀態", example = "ACTIVE")
        ItemStatus status

) {
}
