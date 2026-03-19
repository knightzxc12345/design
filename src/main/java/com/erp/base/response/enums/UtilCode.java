package com.erp.base.response.enums;

public enum UtilCode implements Code {

    INSTANT_ERROR("UTL0001", "日期轉換錯誤"),

    JSON_ERROR("UTL0002", "Json轉換錯誤"),

    NATIVE_ERROR("UTL0003", "SQL轉換錯誤"),

    IMAGE_ERROR("UTL0004", "圖片轉換錯誤"),

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
