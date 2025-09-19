package com.design.entity.enums;

public enum ItemStatus {

    ENABLE("啟用"),

    DISABLE("停用")

    ;

    private final String label;

    ItemStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
