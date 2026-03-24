package com.erp.controller.action.response;

import com.erp.entity.enums.ActionMethod;
import com.erp.entity.enums.ActionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ActionFindResponse(

        @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID uuid,

        @Schema(description = "權限uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
        UUID permissionUuid,

        @Schema(description = "名稱", example = "客戶管理")
        String name,

        @Schema(description = "方法", example = "GET")
        ActionMethod method,

        @Schema(description = "排序", example = "1")
        Integer sort,

        @Schema(description = "狀態", example = "ENABLE")
        ActionStatus status

) {
}
