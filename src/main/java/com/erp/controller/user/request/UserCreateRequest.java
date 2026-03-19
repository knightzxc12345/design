package com.erp.controller.user.request;

import com.erp.base.regex.BaseRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserCreateRequest(

        @Schema(description = "帳號", example = "test")
        @Length(min = 1, max = 64, message = "帳號長度必須為1~64")
        @NotBlank(message = "帳號不得為空")
        String account,

        @Schema(description = "密碼", example = "test")
        @Length(min = 1, max = 64, message = "密碼長度必須為1~64")
        @NotBlank(message = "密碼不得為空")
        String password,

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "信箱", example = "test@gmail.com")
        @Length(min = 0, max = 128, message = "信箱長度必須為0~128")
        @Email(message = "信箱格式錯誤")
        @NotBlank(message = "信箱不得為空")
        String email,

        @Schema(description = "電話", example = "0911111111")
        @Pattern(regexp = BaseRegex.MOBILE, message = "電話格式錯誤")
        @Length(min = 0, max = 10, message = "電話長度必須為10")
        String mobile

) {
}
