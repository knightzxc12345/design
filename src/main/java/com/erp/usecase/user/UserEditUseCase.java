package com.erp.usecase.user;

import com.erp.controller.user.request.UserEditRequest;

import java.util.UUID;

public interface UserEditUseCase {

    void edit(UUID uuid, UserEditRequest request);

}
