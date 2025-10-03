package com.design.entity.enums;

public enum ItemStatus {

    ACTIVE("啟用"),

    INACTIVE("停用")

    ;

    private final String label;

    ItemStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
