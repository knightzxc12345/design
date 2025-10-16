package com.design.controller.quotation.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public record QuotationFindResponse(

        @Schema(description = "報價單號", example = "DE20251010101010")
        String quotationNo,

        @Schema(description = "成本總計", example = "5000")
        BigDecimal totalCostPrice,

        @Schema(description = "報價總計", example = "5000")
        BigDecimal totalPrice,

        @Schema(description = "議價總計", example = "5000")
        BigDecimal totalNegotiatedPrice,

        @Schema(description = "備註", example = "Test")
        String remark,

        @Schema(description = "名稱")
        QuotationFindResponse.Customer customer,

        @Schema(description = "產品")
        List<QuotationFindResponse.Product> products

) {

        public record Customer(

                @Schema(description = "uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
                String uuid,

                @Schema(description = "名稱", example = "Test")
                String name,

                @Schema(description = "電話", example = "0222222222")
                String phone,

                @Schema(description = "傳真", example = "0222222222")
                String fax,

                @Schema(description = "信箱", example = "Test@gmail.com")
                String email,

                @Schema(description = "地址", example = "心北市內湖區")
                String address,

                @Schema(description = "統一編號", example = "12345678")
                String vatNumber,

                @Schema(description = "聯絡人", example = "Test")
                String contactName,

                @Schema(description = "聯絡人電話", example = "0911111111")
                String contactPhone,

                @Schema(description = "備註", example = "Test")
                String remark

        ){
        }

        public record Product(

                @Schema(description = "uuid", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
                String uuid,

                @Schema(description = "名稱", example = "Test")
                String name,

                @Schema(description = "數量", example = "1")
                Integer quantity,

                @Schema(description = "成本金額", example = "100")
                BigDecimal costPrice,

                @Schema(description = "報價金額", example = "100")
                BigDecimal price,

                @Schema(description = "議價金額", example = "100")
                BigDecimal negotiatedPrice

        ){
        }

}
