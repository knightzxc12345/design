package com.erp.controller.supplier.request;

import com.erp.entity.enums.SupplierStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record SupplierEditRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "test")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        String code,

        @Schema(description = "統編", example = "88888888")
        @Length(min = 8, max = 8, message = "統編長度必須為8")
        String taxId,

        @Schema(description = "電話", example = "02-22222222")
        @Length(min = 0, max = 32, message = "電話長度必須為0~32")
        String phone,

        @Schema(description = "傳真", example = "02-22222222")
        @Length(min = 0, max = 32, message = "傳真長度必須為0~32")
        String fax,

        @Schema(description = "傳真", example = "02-22222222")
        String registerAddress,

        @Schema(description = "營業地址", example = "02-22222222")
        String businessAddress,

        @Schema(description = "狀態", example = "ENABLE")
        @NotNull(message = "狀態不得為空")
        SupplierStatus status

) {
}
