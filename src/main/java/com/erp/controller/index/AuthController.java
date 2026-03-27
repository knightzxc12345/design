package com.erp.controller.index;

import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.index.request.LoginRequest;
import com.erp.controller.index.response.LoginResponse;
import com.erp.usecase.auth.AuthLoginUseCase;
import com.erp.usecase.auth.AuthLogoutUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@Tag(name = "認證")
@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthLoginUseCase authLoginUseCase;

    private final AuthLogoutUseCase authLogoutUseCase;

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull LoginRequest request) {
        LoginResponse response = authLoginUseCase.login(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
