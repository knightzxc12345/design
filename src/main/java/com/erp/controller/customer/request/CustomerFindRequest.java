package com.erp.controller.customer.request;

import com.erp.entity.enums.CustomerStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        CustomerStatus status

) {
}
