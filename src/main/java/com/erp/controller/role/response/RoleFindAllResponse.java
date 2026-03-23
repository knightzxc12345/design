package com.erp.controller.role.response;

import com.erp.entity.enums.RoleStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record RoleFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        UUID uuid,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "狀態", example = "ACTIVE")
        RoleStatus status

) {
}
