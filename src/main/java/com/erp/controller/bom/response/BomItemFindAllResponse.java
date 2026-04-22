package com.erp.controller.bom.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record BomItemFindAllResponse(

        @Schema(description = "Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "bom Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID bomUuid,

        @Schema(description = "材料Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID materialUuid,

        @Schema(description = "數量", example = "1")
        BigDecimal quantity

) {
}
