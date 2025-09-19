package com.design.entity.enums;

public enum ProductStatus {

    ENABLE("啟用"),

    DISABLE("停用")

    ;

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
