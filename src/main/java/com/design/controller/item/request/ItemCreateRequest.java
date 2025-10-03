package com.design.controller.item.request;

import com.design.entity.enums.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ItemCreateRequest(

        @Schema(description = "品項名稱", example = "筆記型電腦")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "品項代號", example = "LAPTOP001")
        @Length(min = 1, max = 64, message = "代號長度必須為1~64")
        @NotBlank(message = "代號不得為空")
        String code,

        @Schema(description = "尺寸", example = "15吋")
        @Length(max = 64, message = "尺寸長度不得超過64")
        String dimension,

        @Schema(description = "描述", example = "高效能筆記型電腦")
        @Length(max = 512, message = "描述長度不得超過512")
        String description,

        @Schema(description = "單位", example = "台")
        @Length(min = 1, max = 10, message = "單位長度必須為1~10")
        @NotBlank(message = "單位不得為空")
        String unit,

        @Schema(description = "報價金額", example = "35000")
        @NotNull(message = "價格不得為空")
        BigDecimal price,

        @Schema(description = "供應商 UUID", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        @NotBlank(message = "供應商不得為空")
        String supplierUuid,

        @Schema(description = "品項狀態", example = "ACTIVE")
        @NotNull(message = "狀態不得為空")
        ItemStatus status

) {
}

