package com.erp.base.response.enums;

public enum CategoryCode implements Code {

    NOT_EXISTS("CAT0001", "查無種類"),

    DUPLICATE_NAME("CAT0002", "種類名稱重複"),

    DUPLICATE_CODE("CAT0003", "種類代碼重複"),

    ;

    private final String code;
    private final String message;

    CategoryCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
