package com.design.controller.item.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPageRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword,

        @Min(value = 0, message = "頁數不得小於0")
        @NotNull(message = "頁數不得為空")
        int page,

        @Min(value = 10, message = "頁數不得小於10")
        @NotNull(message = "頁數不得為空")
        int size

) {
}
