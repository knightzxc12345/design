package com.design.controller.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductItem(

        @Schema(description = "uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "品項名稱", example = "筆記型電腦")
        String name,

        @Schema(description = "品項數量", example = "1")
        int quantity

) {
}
