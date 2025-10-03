package com.design.controller.supplier.requst;

import io.swagger.v3.oas.annotations.media.Schema;

public record SupplierFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
