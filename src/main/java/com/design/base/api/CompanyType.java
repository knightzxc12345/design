package com.design.base.api;

public enum CompanyType implements CodeMessage {

    CATHAY("cathay", "國泰世華銀行標準家具報價單"),

    ;

    private final String code;
    private final String message;

    CompanyType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
