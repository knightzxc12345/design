package com.design.usecase.product;

import com.design.controller.product.request.ProductFindRequest;
import com.design.controller.product.request.ProductPageRequest;
import com.design.controller.product.response.ProductFindAllResponse;
import com.design.controller.product.response.ProductFindResponse;
import com.design.controller.product.response.ProductPageResponse;

import java.util.List;

public interface ProductFindUseCase {
    ProductFindResponse findDetail(String uuid);

    List<ProductFindAllResponse> findAll(ProductFindRequest request);

    ProductPageResponse findByPage(ProductPageRequest request);

}
