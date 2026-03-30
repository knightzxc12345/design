package com.erp.base.response.enums;

public enum RedisCode implements Code {

    REDIS_SAVE_ERROR("RED0001", "redis儲存錯誤"),

    REDIS_DELETE_ERROR("RED0002", "redis刪除錯誤"),

    REDIS_GET_ERROR("RED0003", "redis取得錯誤"),

    ;

    private final String code;

    private final String message;

    RedisCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public String getMessage() { return message; }

}
