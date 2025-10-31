package com.design.controller.file.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FileEditRequest(

        @Schema(description = "報價單uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        @NotNull(message = "報價單uuid不得為空")
        String quotationUuid,

        @Schema(description = "標籤清單", example = "test,test")
        @NotNull(message = "標籤清單不得為空")
        List<String> tags,

        @Schema(description = "備註清單", example = "test,test")
        @NotNull(message = "備註清單不得為空")
        List<String> remarks

) {
}
