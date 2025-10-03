package com.design.controller.customer.response;

import com.design.entity.enums.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "信箱", example = "test@gmail.com")
        String email,

        @Schema(description = "統一編號", example = "22222222")
        String vatNumber,

        @Schema(description = "聯絡人名稱", example = "Test")
        String contactName,

        @Schema(description = "聯絡人電話", example = "0900000000")
        String contactPhone,

        @Schema(description = "狀態", example = "ACTIVE")
        CustomerStatus status

) {
}
