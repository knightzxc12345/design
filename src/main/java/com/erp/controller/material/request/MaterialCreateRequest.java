package com.erp.controller.material.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

public record MaterialCreateRequest(

        @Schema(description = "供應商Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "供應商uuid不得為空")
        UUID supplierUuid,

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "test")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        @NotBlank(message = "代碼不得為空")
        String code,

        @Schema(description = "規格", example = "test")
        @Length(min = 1, max = 128, message = "規格長度必須為1~64")
        String spec,

        @Schema(description = "成本", example = "100")
        @Min(value = 0, message = "成本不得小於0")
        @NotNull(message = "成本不得為空")
        BigDecimal cost,

        @Schema(description = "備註", example = "test")
        String remark

) {
}
