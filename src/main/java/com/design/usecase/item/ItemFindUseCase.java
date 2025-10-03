package com.design.usecase.item;

import com.design.controller.item.request.ItemFindRequest;
import com.design.controller.item.request.ItemPageRequest;
import com.design.controller.item.response.ItemFindAllResponse;
import com.design.controller.item.response.ItemFindResponse;
import com.design.controller.item.response.ItemPageResponse;

import java.util.List;

public interface ItemFindUseCase {

    ItemFindResponse findDetail(String uuid);

    List<ItemFindAllResponse> findAll(ItemFindRequest request);

    ItemPageResponse findByPage(ItemPageRequest request);

}
