package com.erp.base.response.enums;

public enum UserRoleCode implements Code {

    NOT_EXISTS("USR0001", "查無使用者角色"),

    ;

    private final String code;
    private final String message;

    UserRoleCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
