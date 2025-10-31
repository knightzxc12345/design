package com.design.controller.file.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileFindRequest(

        @Schema(description = "關鍵字", example = "test")
        String keyword

) {
}
