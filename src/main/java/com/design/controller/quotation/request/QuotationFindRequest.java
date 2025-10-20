package com.design.controller.quotation.request;

import com.design.entity.enums.QuotationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record QuotationFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Schema(description = "開始時間", example = "2025-10-20")
        String startTime,

        @Schema(description = "結束時間", example = "2025-10-20")
        String endTime,

        @Schema(description = "報價單狀態", example = "草稿")
        QuotationStatus quotationStatus

) {
}
