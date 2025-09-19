package com.design.base.api;

public enum UtilCode implements CodeMessage {

    INSTANT_ERROR("UTL0001", "日期轉換錯誤"),

    JSON_ERROR("UTL0002", "Json轉換錯誤"),

    NATIVE_ERROR("UTL0003", "Native錯誤"),

    ;

    private final String code;
    private final String message;

    UtilCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }


}
