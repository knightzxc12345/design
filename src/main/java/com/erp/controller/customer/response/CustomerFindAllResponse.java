package com.erp.controller.customer.response;

import com.erp.entity.enums.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CustomerFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        UUID uuid,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "電話", example = "0222222222")
        String phone,

        @Schema(description = "信箱", example = "test@gmail.com")
        String email,

        @Schema(description = "地址", example = "台北市內湖區")
        String address,

        @Schema(description = "統一編號", example = "22222222")
        String vatNumber,

        @Schema(description = "聯絡人名稱", example = "Test")
        String contactName,

        @Schema(description = "聯絡人電話", example = "0900000000")
        String contactPhone,

        @Schema(description = "狀態", example = "ENABLE")
        CustomerStatus status

) {
}
