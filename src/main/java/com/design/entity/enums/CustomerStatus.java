package com.design.entity.enums;

public enum CustomerStatus {

    ACTIVE("啟用"),

    INACTIVE("停用")

    ;

    private final String label;

    CustomerStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
