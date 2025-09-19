package com.design.base.api.enums;

import com.design.base.api.StatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    @Schema(description = "代碼", example = "A00000")
    private String code;

    @Schema(description = "訊息", example = "成功")
    private String message;

    @Schema(description = "資料")
    private T data;

    public ApiResponse(@NotNull StatusCode status){
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ApiResponse(@NotNull StatusCode status, T data){
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public ApiResponse(@NotNull String code, @NotNull String message){
        this.code = code;
        this.message = message;
    }

}