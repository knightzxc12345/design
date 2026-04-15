package com.erp.base.response.enums;

public enum BrandCode implements Code {

    NOT_EXISTS("BRA0001", "查無品牌"),

    DUPLICATE_NAME("BRA0002", "品牌名稱重複"),

    DUPLICATE_CODE("BRA0003", "品牌代碼重複"),

    ;

    private final String code;
    private final String message;

    BrandCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
