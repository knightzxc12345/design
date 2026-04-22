package com.erp.controller.bom.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record BomItemCreateRequest(

        @Schema(description = "bom Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "bom uuid不得為空")
        UUID bomUuid,

        @Schema(description = "材料Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "材料uuid不得為空")
        UUID materialUuid,

        @Schema(description = "數量", example = "1")
        @NotNull(message = "數量不得為空")
        BigDecimal quantity

) {
}
