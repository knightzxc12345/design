package com.erp.usecase.boom;

import com.erp.controller.bom.request.BomEditRequest;

import java.util.UUID;

public interface BomEditUseCase {

    void edit(UUID uuid, BomEditRequest request);

}
