package com.erp.usecase.item;

import com.erp.base.response.PageResponse;
import com.erp.controller.item.request.ItemFindRequest;
import com.erp.controller.item.request.ItemPageRequest;
import com.erp.controller.item.response.ItemFindAllResponse;
import com.erp.controller.item.response.ItemFindResponse;

import java.util.List;
import java.util.UUID;

public interface ItemFindUseCase {

    ItemFindResponse findDetail(UUID uuid);

    List<ItemFindAllResponse> findAll(UUID productUuid, ItemFindRequest request);

    PageResponse<ItemFindAllResponse> findPage(UUID productUuid, ItemPageRequest request);

}
