package com.erp.usecase.permission;

import com.erp.controller.permission.request.PermissionEditRequest;

import java.util.UUID;

public interface PermissionEditUseCase {

    void bind(UUID roleUuid, PermissionEditRequest request);

}
