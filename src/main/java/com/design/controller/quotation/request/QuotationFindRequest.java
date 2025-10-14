package com.design.controller.quotation.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record QuotationFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
