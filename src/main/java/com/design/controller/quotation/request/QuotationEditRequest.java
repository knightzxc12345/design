package com.design.controller.quotation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record QuotationEditRequest(

        @Schema(description = "客戶id", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        @NotBlank(message = "客戶id不得為空")
        String customerUuid,

        @Schema(description = "備註", example = "test")
        String remark,

        @Schema(description = "產品清單", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        @NotNull(message = "產品清單不得為空")
        List<QuotationEditRequest.Product> products

) {

        public record Product(

                @Schema(description = "產品id", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
                @NotBlank(message = "產品id不得為空")
                String productUuid,

                @Schema(description = "數量", example = "1")
                @NotNull(message = "數量不得為空")
                Integer quantity,

                @Schema(description = "議價金額", example = "100")
                @NotNull(message = "議價金額不得為空")
                BigDecimal negotiatedPrice

        ){
        }

}
