package com.design.base.api;

public enum CustomerCode implements CodeMessage {

    NOT_EXISTS("SUP0001", "查無客戶"),

    DUPLICATE_NAME("SUP0002", "客戶名稱重複"),

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
