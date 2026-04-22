package com.erp.controller.bom.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record BomItemFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
