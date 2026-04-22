package com.erp.usecase.boom;

import com.erp.base.response.PageResponse;
import com.erp.controller.bom.request.BomFindRequest;
import com.erp.controller.bom.request.BomPageRequest;
import com.erp.controller.bom.response.BomFindAllResponse;
import com.erp.controller.bom.response.BomFindResponse;

import java.util.List;
import java.util.UUID;

public interface BomFindUseCase {

    BomFindResponse findDetail(UUID uuid);

    List<BomFindAllResponse> findAll(UUID itemUuid, BomFindRequest request);

    PageResponse<BomFindAllResponse> findPage(UUID itemUuid, BomPageRequest request);

}
