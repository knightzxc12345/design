package com.erp.controller.bom.response;

import com.erp.entity.enums.BomStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BomFindResponse(

        @Schema(description = "Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "項目Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID itemUuid,

        @Schema(description = "版本", example = "test")
        String version,

        @Schema(description = "狀態", example = "ENABLE")
        BomStatus status,

        @Schema(description = "品項清單")
        List<BomFindResponse.Item> items

) {

        public record Item(

                @Schema(description = "Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
                UUID uuid,

                @Schema(description = "材料Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
                UUID materialUuid,

                @Schema(description = "數量", example = "1")
                BigDecimal quantity

        ){}

}
