package com.erp.base.response.enums;

public enum BomCode implements Code {

    NOT_EXISTS("BOM0001", "查無BOM"),

    DUPLICATE_VERSION("BOM0002", "BOM版本重複"),

    ;

    private final String code;
    private final String message;

    BomCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
