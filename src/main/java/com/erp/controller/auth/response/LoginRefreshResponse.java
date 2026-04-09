package com.erp.controller.auth.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRefreshResponse(

        @Schema(description = "token", example = "token")
        String token

) {
}
