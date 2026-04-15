package com.erp.base.response.enums;

public enum ItemCode implements Code {

    NOT_EXISTS("ITE0001", "查無品項"),

    DUPLICATE_SKU_CODE("ITE0002", "品項sku code重複"),

    ;

    private final String code;
    private final String message;

    ItemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
