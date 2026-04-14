package com.erp.controller.category.request;

import com.erp.entity.enums.CategoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CategoryEditRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "test")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        String code,

        @Schema(description = "說明", example = "02-22222222")
        String description,

        @Schema(description = "狀態", example = "ENABLE")
        @NotNull(message = "狀態不得為空")
        CategoryStatus status


) {
}
