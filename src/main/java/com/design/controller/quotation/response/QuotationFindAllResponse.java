package com.design.controller.quotation.response;

import com.design.entity.enums.QuotationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record QuotationFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "報價單編號", example = "DE20251030101010")
        String quotationNo,

        @Schema(description = "客戶名稱", example = "test")
        String customerName,

        @Schema(description = "成本總計", example = "5000")
        BigDecimal totalCostPrice,

        @Schema(description = "總計", example = "5000")
        BigDecimal totalPrice,

        @Schema(description = "議價總計", example = "5000")
        BigDecimal totalNegotiatedPrice,

        @Schema(description = "狀態", example = "草稿")
        QuotationStatus quotationStatus,

        @Schema(description = "建立時間", example = "2025-01-01 12:00:00")
        String createTime,

        @Schema(description = "建立人員", example = "test")
        String createUser

) {
}
