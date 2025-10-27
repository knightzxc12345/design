package com.design.controller.item.response;

import com.design.entity.enums.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ItemFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "編號", example = "筆記型電腦")
        String no,

        @Schema(description = "品名", example = "筆記型電腦")
        String name,

        @Schema(description = "規格", example = "15吋")
        String dimension,

        @Schema(description = "描述", example = "15吋")
        String description,

        @Schema(description = "單位", example = "台")
        String unit,

        @Schema(description = "金額", example = "1000")
        BigDecimal price,

        @Schema(description = "供應商uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String supplierUuid,

        @Schema(description = "供應商名稱", example = "宏碁")
        String supplierName,

        @Schema(description = "品項狀態", example = "ACTIVE")
        ItemStatus status

) {
}
