package com.design.controller.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductItem(

        @Schema(description = "uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "品項名稱", example = "Test")
        String name,

        @Schema(description = "供應商uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String supplierUuid,

        @Schema(description = "供應商名稱", example = "Test")
        String supplierName,

        @Schema(description = "品項數量", example = "1")
        int quantity,

        @Schema(description = "品項金額", example = "10000")
        BigDecimal price

) {
}
