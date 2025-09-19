package com.design.base.api;

public enum StatusCode {

    SUCCESS("S0001", "新增成功"),

    FAILED("S0002", "新增失敗"),

    SYSTEM_ERROR("S0004", "系統錯誤")

    ;

    private final String code;
    private final String message;

    StatusCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}