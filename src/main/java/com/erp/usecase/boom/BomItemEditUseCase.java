package com.erp.usecase.boom;

import com.erp.controller.bom.request.BomItemEditRequest;

import java.util.UUID;

public interface BomItemEditUseCase {

    void edit(UUID uuid, BomItemEditRequest request);

}
