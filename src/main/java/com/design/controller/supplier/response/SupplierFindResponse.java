package com.design.controller.supplier.response;

import com.design.entity.enums.SupplierStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record SupplierFindResponse(

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "統一編號", example = "22222222")
        String vatNumber,

        @Schema(description = "電話", example = "02-22222222")
        String phone,

        @Schema(description = "傳真", example = "02-22222222")
        String fax,

        @Schema(description = "信箱", example = "test@gmail.com")
        String email,

        @Schema(description = "地址", example = "台北市內湖區")
        String address,

        @Schema(description = "聯絡人名稱", example = "Test")
        String contactName,

        @Schema(description = "聯絡人電話", example = "0900000000")
        String contactPhone,

        @Schema(description = "備註", example = "這是備註")
        String remark,

        @Schema(description = "狀態", example = "ACTIVE")
        SupplierStatus status

) {
}
