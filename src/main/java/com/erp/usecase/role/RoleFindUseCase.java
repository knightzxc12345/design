package com.erp.usecase.role;

import com.erp.controller.role.request.RoleFindRequest;
import com.erp.controller.role.response.RoleFindAllResponse;
import com.erp.controller.role.response.RoleFindResponse;

import java.util.List;
import java.util.UUID;

public interface RoleFindUseCase {

    RoleFindResponse findDetail(UUID uuid);

    List<RoleFindAllResponse> findAll(RoleFindRequest request);

}
