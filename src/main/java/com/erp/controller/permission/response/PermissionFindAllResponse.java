package com.erp.controller.permission.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record PermissionFindAllResponse(

        @Schema(description = "權限")
        PermissionFindAllResponse.Permission permission,

        @Schema(description = "子權限")
        List<Permission> parentPermissions

) {

        public record Permission(

                @Schema(description = "uuid", example = "319e7e1d-ca74-4500-b2f9-d3d2d2a6ffbe")
                UUID uuid,

                @Schema(description = "名稱", example = "客戶管理")
                String name,

                @Schema(description = "網址", example = "/customer/get")
                String url,

                @Schema(description = "動作", example = "CUSTOMER_GET")
                String action

        ){}

}
