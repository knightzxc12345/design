package com.design.model;

import java.math.BigDecimal;

public record PriceSummary(

        BigDecimal totalCostPrice,

        BigDecimal totalPrice,

        BigDecimal totalNegotiatedPrice

) {
}
