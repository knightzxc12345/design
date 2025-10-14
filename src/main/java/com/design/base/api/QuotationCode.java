package com.design.base.api;

public enum QuotationCode implements CodeMessage {

    NOT_EXISTS("QUO0001", "查無報價單"),

    ;

    private final String code;
    private final String message;

    QuotationCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
