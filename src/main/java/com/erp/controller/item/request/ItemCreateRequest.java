package com.erp.controller.item.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCreateRequest(

        @Schema(description = "產品Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "產品uuid不得為空")
        UUID productUuid,

        @Schema(description = "sku code", example = "test")
        @Length(min = 1, max = 64, message = "sku code長度必須為1~64")
        @NotBlank(message = "sku code不得為空")
        String skuCode,

        @Schema(description = "規格", example = "test")
        @Length(min = 1, max = 128, message = "規格長度必須為1~64")
        String spec,

        @Schema(description = "價格", example = "100")
        @Min(value = 0, message = "價格不得小於0")
        @NotNull(message = "價格不得為空")
        BigDecimal price,

        @Schema(description = "成本", example = "100")
        @Min(value = 0, message = "成本不得小於0")
        @NotNull(message = "成本不得為空")
        BigDecimal cost

) {
}
