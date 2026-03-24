package com.erp.usecase.permission;

import com.erp.controller.permission.request.PermissionFindRequest;
import com.erp.controller.permission.response.PermissionFindAllResponse;

import java.util.List;

public interface PermissionFindUseCase {

    List<PermissionFindAllResponse> findAll(PermissionFindRequest request);

}
