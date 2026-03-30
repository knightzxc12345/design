package com.erp.base.response.enums;

public enum SystemCode implements Code {

    SUCCESS("SYS0001", "成功"),

    FAILED("SYS0002", "失敗"),

    SYSTEM_ERROR("SYS0003", "系統錯誤"),

    LOGIN_FAIL("SYS0004", "登入失敗"),

    JWT_USER_NOT_FOUND("SYS0005", "查無使用者"),

    JWT_TOKEN_EXPIRED("SYS0006", "token過期"),

    PERMISSION_DENIED("SYS0007", "查無權限"),

    TOKEN_UNDEFINED("SYS0008", "查無Token"),

    TOKEN_INVALID_TYPE("SYS009", "Token型別錯誤")

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