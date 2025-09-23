package com.design.entity.enums;

public enum SupplierStatus {

    ACTIVE("啟用"),

    INACTIVE("停用"),

    ;

    private final String label;

    SupplierStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
