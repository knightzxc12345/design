package com.design.controller.item.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record ItemFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "供應商唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String supplierUuid

) {
}
