package com.design.controller.product.request;

import com.design.entity.enums.ProductStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record ProductEditRequest(

        @Schema(description = "名稱", example = "test")
        @Length(min = 1, max = 64, message = "名稱長度必須為1~64")
        @NotBlank(message = "名稱不得為空")
        String name,

        @Schema(description = "代碼", example = "test")
        @Length(min = 1, max = 64, message = "代碼長度必須為1~64")
        @NotBlank(message = "代碼不得為空")
        String code,

        @Schema(description = "描述", example = "test")
        @Length(max = 512, message = "描述長度不得超過512")
        String description,

        @Schema(description = "尺寸", example = "15吋")
        @Length(max = 64, message = "尺寸長度不得超過64")
        String dimension,

        @Schema(description = "單位", example = "台")
        @Length(min = 1, max = 10, message = "單位長度必須為1~10")
        @NotBlank(message = "單位不得為空")
        String unit,

        @Schema(description = "報價金額", example = "35000")
        @NotNull(message = "價格不得為空")
        BigDecimal price,

        @Schema(description = "品項")
        @NotBlank(message = "品項不得為空")
        List<Item> items,

        @Schema(description = "產品狀態", example = "ACTIVE")
        @NotNull(message = "笧品不得為空")
        ProductStatus status

) {

        public record Item(

                @Schema(description = "uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
                String uuid,

                @Schema(description = "品項數量", example = "1")
                int quantity

        ){}

}
