package com.design.base.api;

public enum ItemCode implements CodeMessage {

    NOT_EXISTS("ITE0001", "查無項目"),

    DUPLICATE_CODE("ITE0002", "代碼重複"),

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
