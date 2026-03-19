package com.erp.entity.enums;

public enum ActionMethod implements EnumBase<Integer, String> {

    POST(1, "POST"),

    PUT(2, "PUT"),

    PATCH(3, "PATCH"),

    DELETE(4, "DELETE"),

    GET(5, "GET"),

    ;

    private int method;

    private String name;

    ActionMethod(int method, String name) {
        this.method = method;
        this.name = name;
    }

    @Override
    public Integer get() {
        return method;
    }

    public String getName() {
        return name;
    }

}