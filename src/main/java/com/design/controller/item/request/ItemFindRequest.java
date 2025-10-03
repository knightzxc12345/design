package com.design.controller.item.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
