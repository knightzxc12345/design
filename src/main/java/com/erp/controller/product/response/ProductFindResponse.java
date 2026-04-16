package com.erp.controller.product.response;

import com.erp.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record ProductFindResponse(

        @Schema(description = "產品Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "種類Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "種類uuid不得為空")
        UUID categoryUuid,

        @Schema(description = "種類Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "種類uuid不得為空")
        UUID brandUuid,

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "test")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        @NotBlank(message = "代碼不得為空")
        String code,

        @Schema(description = "說明", example = "test")
        String description,

        @Schema(description = "狀態", example = "ENABLE")
        ProductStatus status

) {
}
