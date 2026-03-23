package com.erp.controller.role.response;

import com.erp.entity.enums.RoleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record RoleFindResponse(

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "狀態", example = "ACTIVE")
        RoleStatus status

) {
}
