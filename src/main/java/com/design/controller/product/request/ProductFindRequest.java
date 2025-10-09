package com.design.controller.product.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProductFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
