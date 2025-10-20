package com.design.usecase.item;

import com.design.controller.item.request.ItemEditRequest;

public interface ItemEditUseCase {

    void edit(String uuid, ItemEditRequest request);

}
