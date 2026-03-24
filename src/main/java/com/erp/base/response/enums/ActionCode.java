package com.erp.base.response.enums;

public enum ActionCode implements Code {

    NOT_EXISTS("ACT0001", "查無動作"),

    INVALID_EXIT("ACT0002", "存在無效的動作ID"),

    ;

    private final String code;
    private final String message;

    ActionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
