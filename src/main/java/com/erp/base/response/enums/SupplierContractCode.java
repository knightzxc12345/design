package com.erp.base.response.enums;

public enum SupplierContractCode implements Code {

    NOT_EXISTS("SUP0001", "查無供應商聯絡人"),

    ;

    private final String code;
    private final String message;

    SupplierContractCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
