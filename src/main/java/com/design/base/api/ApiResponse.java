package com.design.base.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T, C extends CodeMessage> {

    @Schema(description = "代碼", example = "A00000")
    private String code;

    @Schema(description = "訊息", example = "成功")
    private String message;

    @Schema(description = "資料")
    private T data;

    public ApiResponse(C status){
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ApiResponse(C status, T data){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public ApiResponse(@NotNull String code, @NotNull String message){
        this.code = code;
        this.message = message;
    }

    public ApiResponse(@NotNull String code, @NotNull String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}