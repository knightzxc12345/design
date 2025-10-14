package com.design.entity.enums;

public enum QuotationStatus {

    DRAFT("草稿"),

    NEGOTIATING("議價中"),

    APPROVED("已核准"),

    REJECTED("已拒絕"),

    COMPLETED("已完成"),

    CANCELLED("已取消"),

    ;

    private final String label;

    QuotationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
