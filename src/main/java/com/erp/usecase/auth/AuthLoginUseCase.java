package com.erp.usecase.auth;

import com.erp.controller.index.request.LoginRequest;
import com.erp.controller.index.response.LoginResponse;

public interface AuthLoginUseCase {

    LoginResponse login(LoginRequest request);

}
