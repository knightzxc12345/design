package com.erp.controller.supplier.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SupplierContractFindAllResponse(

        @Schema(description = "供應商Uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID supplierUuid,

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "電話", example = "02-22222222")
        String phone,

        @Schema(description = "信箱", example = "test@gmail.com")
        String email,

        @Schema(description = "職稱", example = "管理者")
        String title

) {
}
