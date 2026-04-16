package com.erp.usecase.brand;

import com.erp.base.response.PageResponse;
import com.erp.controller.brand.request.BrandFindRequest;
import com.erp.controller.brand.request.BrandPageRequest;
import com.erp.controller.brand.response.BrandFindAllResponse;
import com.erp.controller.brand.response.BrandFindResponse;

import java.util.List;
import java.util.UUID;

public interface BrandFindUseCase {

    BrandFindResponse findDetail(UUID uuid);

    List<BrandFindAllResponse> findAll(BrandFindRequest request);

    PageResponse<BrandFindAllResponse> findPage(BrandPageRequest request);

}
