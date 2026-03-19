package com.erp.usecase.user;

import com.erp.controller.user.request.UserCreateRequest;

public interface UserCreateUseCase {

    void create(UserCreateRequest request);

}
