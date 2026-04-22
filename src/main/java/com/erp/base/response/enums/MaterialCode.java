package com.erp.base.response.enums;

public enum MaterialCode implements Code {

    NOT_EXISTS("MAT0001", "查無材料"),

    DUPLICATE_CODE("MAT0002", "材料代碼重複"),

    ;

    private final String code;
    private final String message;

    MaterialCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
