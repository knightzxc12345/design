package com.erp.base.response.enums;

public enum SupplierCode implements Code {

    NOT_EXISTS("SUP0001", "查無供應商"),

    DUPLICATE_NAME("SUP0002", "供應商名稱重複"),

    DUPLICATE_CODE("SUP0003", "供應商代碼重複"),

    ;

    private final String code;
    private final String message;

    SupplierCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
