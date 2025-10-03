package com.design.controller.customer.request;

import com.design.base.regex.BaseRegex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CustomerCreateRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "電話", example = "02-22222222")
        @Length(min = 0, max = 20, message = "電話長度必須為0~20")
        String phone,

        @Schema(description = "傳真", example = "02-22222222")
        @Length(min = 0, max = 32, message = "傳真長度必須為0~32")
        String fax,

        @Schema(description = "信箱", example = "test@gmail.com")
        @Length(min = 0, max = 128, message = "信箱長度必須為0~128")
        @Email(message = "信箱格式錯誤")
        String email,

        @Schema(description = "地址", example = "台北市內湖區")
        String address,


        @Schema(description = "統一編號", example = "12345678")
        @Pattern(regexp = BaseRegex.EMPTY + BaseRegex.VAT_NUMBER, message = "統編格式錯誤")
        String vatNumber,

        @Schema(description = "聯絡人名稱", example = "test")
        @Length(min = 0, max = 64, message = "聯絡人名稱長度必須為0~64")
        String contactName,

        @Schema(description = "聯絡人電話", example = "0911111111")
        @Length(min = 0, max = 32, message = "聯絡人電話長度必須為0~32")
        String contactPhone,

        @Schema(description = "備註", example = "我是備註")
        String remark

) {
}
