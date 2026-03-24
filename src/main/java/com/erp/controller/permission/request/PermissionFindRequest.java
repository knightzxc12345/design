package com.erp.controller.permission.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PermissionFindRequest(

        @Schema(description = "角色Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID roleUuid

) {
}
