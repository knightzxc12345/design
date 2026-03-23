package com.erp.base.response.enums;

public enum PermissionCode implements Code {

    NOT_EXISTS("PER0001", "查無權限"),

    DUPLICATE_NAME("PER0002", "權限名稱重複"),

    ;

    private final String code;
    private final String message;

    PermissionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
