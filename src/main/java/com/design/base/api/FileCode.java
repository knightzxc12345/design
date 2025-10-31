package com.design.base.api;

public enum FileCode implements CodeMessage {

    FILE_CONTENT_ERROR("FIL0001", "檔案規格錯誤"),

    ;

    private final String code;
    private final String message;

    FileCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
