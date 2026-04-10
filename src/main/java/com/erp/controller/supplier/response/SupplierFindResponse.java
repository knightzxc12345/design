package com.erp.controller.supplier.response;

import com.erp.entity.enums.SupplierStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record SupplierFindResponse(

        @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "名稱", example = "test")
        String name,

        @Schema(description = "代碼", example = "test")
        String code,

        @Schema(description = "統編", example = "88888888")
        String taxId,

        @Schema(description = "電話", example = "02-22222222")
        String phone,

        @Schema(description = "傳真", example = "02-22222222")
        String fax,

        @Schema(description = "傳真", example = "02-22222222")
        String registerAddress,

        @Schema(description = "營業地址", example = "02-22222222")
        String businessAddress,

        @Schema(description = "狀態", example = "ENABLE")
        SupplierStatus status,

        @Schema(description = "聯絡人清單")
        List<SupplierFindResponse.Contract> contracts

) {

        public record Contract(

                @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
                UUID uuid,

                @Schema(description = "名稱", example = "test")
                String name,

                @Schema(description = "電話", example = "02-22222222")
                String phone,

                @Schema(description = "信箱", example = "test@gmail.com")
                String email,

                @Schema(description = "職稱", example = "test")
                String title

        ){}

}
