package com.erp.controller.role.request;

import com.erp.entity.enums.RoleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RoleEditRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "狀態", example = "ACTIVE")
        RoleStatus status

) {
}
