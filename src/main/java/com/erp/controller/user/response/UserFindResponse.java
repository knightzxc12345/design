package com.erp.controller.user.response;

import com.erp.entity.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserFindResponse(

        @Schema(description = "帳號", example = "Test")
        String account,

        @Schema(description = "名稱", example = "Test")
        String name,

        @Schema(description = "信箱", example = "test@gmail.com")
        String email,

        @Schema(description = "手機", example = "0911111111")
        String mobile,

        @Schema(description = "狀態", example = "ACTIVE")
        UserStatus status

) {
}
