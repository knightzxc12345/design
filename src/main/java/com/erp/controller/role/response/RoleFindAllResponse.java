package com.erp.controller.role.response;

import com.erp.entity.enums.RoleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record RoleFindAllResponse(

        @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "狀態", example = "ACTIVE")
        RoleStatus status

) {
}
