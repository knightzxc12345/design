package com.erp.controller.supplier.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record SupplierContractEditRequest(

        @Schema(description = "供應商Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "供應商uuid不得為空")
        UUID supplierUuid,

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 32, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "電話", example = "02-22222222")
        @Length(min = 1, max = 32, message = "電話長度必須為1~64")
        String phone,

        @Schema(description = "信箱", example = "test@gmail.com")
        @Length(min = 1, max = 128, message = "信箱長度必須為1~128")
        String email,

        @Schema(description = "職稱", example = "管理者")
        @Length(min = 1, max = 32, message = "職稱長度必須為1~128")
        @NotBlank(message = "職稱不得為空")
        String title

) {
}
