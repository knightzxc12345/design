package com.erp.controller.action.request;

import com.erp.entity.enums.ActionMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ActionCreateRequest(

        @Schema(description = "權限uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        @NotNull(message = "權限uuid不得為空")
        UUID permiossionUuid,

        @Schema(description = "名稱", example = "客戶管理")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "方法", example = "GET")
        @NotNull(message = "方法不得為空")
        ActionMethod method,

        @Schema(description = "排序", example = "1")
        @NotNull(message = "排序不得為空")
        Integer sort

) {
}
