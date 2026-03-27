package com.erp.base.response.enums;

public enum UserCode implements Code {

    NOT_EXISTS("USE0001", "查無使用者"),

    DUPLICATE_ACCOUNT("USE0002", "使用者帳號重複"),

    DUPLICATE_NAME("USE0003", "使用者名稱重複"),

    DUPLICATE_EMAIL("USE0004", "使用者信箱重複"),

    LOGIN_FAIL("USE0005", "帳號或密碼錯誤"),

    ;

    private final String code;
    private final String message;

    UserCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
