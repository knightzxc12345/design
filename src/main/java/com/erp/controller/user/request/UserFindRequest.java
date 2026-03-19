package com.erp.controller.user.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
