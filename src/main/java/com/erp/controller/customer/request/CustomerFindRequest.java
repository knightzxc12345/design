package com.erp.controller.customer.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
