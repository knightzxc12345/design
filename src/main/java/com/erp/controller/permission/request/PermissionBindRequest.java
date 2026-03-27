package com.erp.controller.permission.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record PermissionBindRequest(

        @Schema(description = "權限")
        List<PermissionBindRequest.Permission> permissions

) {

        public record Permission(

                @Schema(description = "父權限Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
                @NotNull(message = "權限uuid不得為空")
                UUID permissionUuid,

                @Schema(description = "子權限")
                List<PermissionBindRequest.Permission> children,

                @Schema(description = "動作權限Uuid清單", example = "[319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe, 319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbf]")
                @NotNull(message = "權限uuid清單不得為空")
                List<UUID> actionUuids

        ){}

}
