package com.erp.usecase.product;

import com.erp.base.response.PageResponse;
import com.erp.controller.product.request.ProductFindRequest;
import com.erp.controller.product.request.ProductPageRequest;
import com.erp.controller.product.response.ProductFindAllResponse;
import com.erp.controller.product.response.ProductFindResponse;

import java.util.List;
import java.util.UUID;

public interface ProductFindUseCase {

    ProductFindResponse findDetail(UUID uuid);

    List<ProductFindAllResponse> findAll(UUID brandUuid, UUID categoryUuid, ProductFindRequest request);

    PageResponse<ProductFindAllResponse> findPage(UUID brandUuid, UUID categoryUuid, ProductPageRequest request);

}
