package com.erp.usecase.boom;

import com.erp.controller.bom.response.BomItemFindAllResponse;
import com.erp.controller.bom.response.BomItemFindResponse;

import java.util.List;
import java.util.UUID;

public interface BomItemFindUseCase {

    BomItemFindResponse findDetail(UUID uuid);

    List<BomItemFindAllResponse> findAll(UUID bomUuid);

}
