package com.design.usecase.quotation.model;

import java.math.BigDecimal;

public record PriceSummary(

        BigDecimal totalCostPrice,

        BigDecimal totalPrice,

        BigDecimal totalNegotiatedPrice

) {
}
