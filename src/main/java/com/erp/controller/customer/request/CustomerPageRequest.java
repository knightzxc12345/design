package com.erp.controller.customer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CustomerPageRequest(

        @Min(value = 0, message = "頁數不得小於0")
        @NotNull(message = "頁數不得為空")
        int page,

        @Min(value = 10, message = "頁數不得小於10")
        @NotNull(message = "頁數不得為空")
        int size,

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
