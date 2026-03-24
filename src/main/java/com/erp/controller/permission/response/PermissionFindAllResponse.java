package com.erp.controller.permission.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PermissionFindAllResponse(

        @Schema(description = "權限")
        PermissionFindAllResponse.Permission permission,

        @Schema(description = "子權限")
        List<Permission> parentPermissions

) {

        public record Permission(

                @Schema(description = "名稱", example = "客戶管理")
                String name,

                @Schema(description = "網址", example = "/customer/get")
                String url,

                @Schema(description = "動作", example = "CUSTOMER_GET")
                String action

        ){}

}
