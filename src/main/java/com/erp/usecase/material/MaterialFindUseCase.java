package com.erp.usecase.material;

import com.erp.base.response.PageResponse;
import com.erp.controller.material.request.MaterialFindRequest;
import com.erp.controller.material.request.MaterialPageRequest;
import com.erp.controller.material.response.MaterialFindAllResponse;
import com.erp.controller.material.response.MaterialFindResponse;

import java.util.List;
import java.util.UUID;

public interface MaterialFindUseCase {

    MaterialFindResponse findDetail(UUID uuid);

    List<MaterialFindAllResponse> findAll(MaterialFindRequest request);

    PageResponse<MaterialFindAllResponse> findPage(MaterialPageRequest request);

}
