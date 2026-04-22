package com.erp.controller.bom.response;

import com.erp.entity.enums.BomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record BomFindAllResponse(

        @Schema(description = "Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "uuid不得為空")
        UUID uuid,

        @Schema(description = "項目Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "項目uuid不得為空")
        UUID itemUuid,

        @Schema(description = "版本", example = "test")
        @Length(min = 1, max = 5, message = "版本長度必須為1~5")
        @NotBlank(message = "版本不得為空")
        String version,

        @Schema(description = "狀態", example = "ENABLE")
        @NotNull(message = "狀態不得為空")
        BomStatus status

) {
}
