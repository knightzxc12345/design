package com.design.entity.enums;

public enum ProductStatus {

    ACTIVE("啟用"),

    INACTIVE("停用")

    ;

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
