package com.erp.controller.index.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record LoginResult(

        @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID userUuid,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "accessToken", example = "token")
        String accessToken,

        @Schema(description = "refreshToken", example = "token")
        String refreshToken

) {
}
