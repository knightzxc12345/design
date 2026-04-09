package com.erp.controller.auth;

import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.auth.request.LoginRequest;
import com.erp.controller.auth.response.LoginRefreshResponse;
import com.erp.controller.auth.response.LoginResponse;
import com.erp.usecase.auth.AuthLoginUseCase;
import com.erp.usecase.auth.AuthLogoutUseCase;
import com.erp.usecase.auth.AuthRefreshUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@Tag(name = "認證")
@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthLoginUseCase authLoginUseCase;

    private final AuthRefreshUseCase authRefreshUseCase;

    private final AuthLogoutUseCase authLogoutUseCase;

    @Operation(summary = "建立")
    @PostMapping(
            value = "/login/v1"
    )
    public CustomResponse login(
            @RequestBody @Validated @NotNull LoginRequest request) {
        LoginResponse response = authLoginUseCase.login(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "更新token")
    @PatchMapping(
            value = "/refresh/v1"
    )
    public CustomResponse refresh() {
        LoginRefreshResponse response = authRefreshUseCase.refresh();
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
