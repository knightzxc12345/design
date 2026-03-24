package com.erp.usecase.permission;

import com.erp.controller.permission.request.PermissionBindRequest;
import com.erp.controller.permission.request.PermissionEditRequest;

import java.util.UUID;

public interface PermissionEditUseCase {

    void edit(UUID permissionUuid, PermissionEditRequest request);

    void bind(UUID roleUuid, PermissionBindRequest request);

}
