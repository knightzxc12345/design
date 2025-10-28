package com.design.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Quotation {

    private String customerName;

    private String customerPhone;

    private String customerAddress;

    private String customerContactName;

    private String reportDate;

    private BigDecimal total;

    private BigDecimal tax;

    private BigDecimal totalWithTax;

    private List<Quotation.Detail> details;

    @Getter
    @Setter
    public static class Detail {

        private int index;

        private String productNo;

        private String productName;

        private String productDimension;

        private Integer productQuantity;

        private String productUnit;

        private BigDecimal productCost;

        private BigDecimal productNegotiated;

    }

}
