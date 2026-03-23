package com.erp.usecase.role;

import com.erp.controller.role.request.RoleEditRequest;

import java.util.UUID;

public interface RoleEditUseCase {

    void edit(UUID uuid, RoleEditRequest request);

}
