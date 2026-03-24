package com.erp.controller.permission.request;

import com.erp.entity.enums.PermissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record PermissionEditRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 32, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "auth:user:create")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        @NotBlank(message = "代碼不得為空")
        String code,

        @Schema(description = "父權限Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID parentUuid,

        @Schema(description = "排序", example = "1")
        @NotNull(message = "排序不得為空")
        Integer sort,

        @Schema(description = "狀態", example = "ENABLE")
        @NotNull(message = "狀態不得為空")
        PermissionStatus status

) {
}
