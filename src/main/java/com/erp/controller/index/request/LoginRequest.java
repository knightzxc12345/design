package com.erp.controller.index.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(

        @Schema(description = "帳號", example = "test")
        @Length(min = 1, max = 32, message = "帳號長度必須為1~32")
        @NotBlank(message = "帳號不得為空")
        String account,

        @Schema(description = "密碼", example = "test")
        @Length(min = 1, max = 128, message = "名稱長度必須為1~128")
        @NotBlank(message = "密碼不得為空")
        String password

) {
}
