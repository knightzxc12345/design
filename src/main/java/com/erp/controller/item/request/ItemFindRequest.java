package com.erp.controller.item.request;

import com.erp.entity.enums.ItemStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record ItemFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "狀態", example = "ENABLE")
        ItemStatus status

) {
}
