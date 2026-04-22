package com.erp.base.response.enums;

public enum BomItemCode implements Code {

    NOT_EXISTS("BOM0001", "查無BOM明細"),

    DUPLICATE_MATERIAL("BOM0002", "BOM材料重複"),

    ;

    private final String code;
    private final String message;

    BomItemCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
