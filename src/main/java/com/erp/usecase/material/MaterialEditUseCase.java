package com.erp.usecase.material;

import com.erp.controller.material.request.MaterialEditRequest;

import java.util.UUID;

public interface MaterialEditUseCase {

    void edit(UUID uuid, MaterialEditRequest request);

}
