package com.erp.base.response.enums;

public enum CustomerCode implements Code {

    NOT_EXISTS("CUS0001", "查無客戶"),

    DUPLICATE_NAME("CUS0002", "客戶名稱重複"),

    DUPLICATE_EMAIL("CUS0003", "客戶信箱重複"),

    ;

    private final String code;
    private final String message;

    CustomerCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
