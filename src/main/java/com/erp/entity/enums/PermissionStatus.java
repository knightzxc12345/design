package com.erp.entity.enums;

public enum PermissionStatus implements EnumBase<Integer, String> {

    DISABLE(0, "停用"),

    ENABLE(1, "啟用"),

    ;

    private int status;

    private String name;

    PermissionStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    @Override
    public Integer get() {
        return status;
    }

    @Override
    public String getName() {
        return name;
    }

}
