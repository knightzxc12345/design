package com.design.base.api;

public enum SystemCode implements CodeMessage {

    SUCCESS("SYS0001", "成功"),

    FAILED("SYS0002", "失敗"),

    SYSTEM_ERROR("SYS0003", "系統錯誤"),

    LOGIN_FAIL("SYS0004", "登入失敗"),

    ;

    private final String code;
    private final String message;

    SystemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}