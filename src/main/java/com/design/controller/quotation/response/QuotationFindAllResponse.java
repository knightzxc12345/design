package com.design.controller.quotation.response;

import com.design.entity.enums.QuotationStatus;

import java.math.BigDecimal;

public record QuotationFindAllResponse(

        String uuid,

        String quotationNo,

        String customerName,

        BigDecimal totalCostPrice,

        BigDecimal totalPrice,

        BigDecimal totalNegotiatedPrice,

        QuotationStatus quotationStatus,

        String createTime,

        String createUser

) {
}
