package com.erp.controller.action.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ActionFindRequest(

        @Schema(description = "權限Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID permissionUuid

) {
}
