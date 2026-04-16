package com.erp.usecase.item;

import com.erp.controller.item.request.ItemEditRequest;

import java.util.UUID;

public interface ItemEditUseCase {

    void edit(UUID uuid, ItemEditRequest request);

}
