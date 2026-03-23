package com.erp.base.response.enums;

public enum RoleCode implements Code {

    NOT_EXISTS("ROL0001", "查無角色"),

    DUPLICATE_NAME("ROL0002", "角色名稱重複"),

    ;

    private final String code;
    private final String message;

    RoleCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
