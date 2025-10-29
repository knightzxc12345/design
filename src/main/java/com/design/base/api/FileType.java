package com.design.base.api;

public enum FileType implements CodeMessage {

    EXCEL("excel", "excel"),

    PDF("pdf", "pdf"),

    ;

    private final String code;
    private final String message;

    FileType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
