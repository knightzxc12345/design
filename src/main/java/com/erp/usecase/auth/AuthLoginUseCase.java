package com.erp.usecase.auth;

import com.erp.controller.auth.request.LoginRequest;
import com.erp.controller.auth.response.LoginResponse;

public interface AuthLoginUseCase {

    LoginResponse login(LoginRequest request);

}
