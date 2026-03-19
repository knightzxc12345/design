package com.erp.usecase.customer;

import com.erp.base.response.PageResponse;
import com.erp.controller.customer.request.CustomerFindRequest;
import com.erp.controller.customer.request.CustomerPageRequest;
import com.erp.controller.customer.response.CustomerFindAllResponse;
import com.erp.controller.customer.response.CustomerFindResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerFindUseCase {

    CustomerFindResponse findDetail(UUID uuid);

    List<CustomerFindAllResponse> findAll(CustomerFindRequest request);

    PageResponse<CustomerFindAllResponse> findByPage(CustomerPageRequest request);

}
