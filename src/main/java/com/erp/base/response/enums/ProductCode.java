package com.erp.base.response.enums;

public enum ProductCode implements Code {

    NOT_EXISTS("PRO0001", "查無產品"),

    DUPLICATE_NAME("PRO0002", "產品名稱重複"),

    DUPLICATE_CODE("PRO0003", "產品代碼重複"),

    ;

    private final String code;
    private final String message;

    ProductCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
