package com.erp.controller.supplier.request;

import com.erp.entity.enums.SupplierStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record SupplierFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        SupplierStatus status

) {
}
