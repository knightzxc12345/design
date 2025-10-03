package com.design.usecase.customer;

import com.design.controller.customer.request.CustomerFindRequest;
import com.design.controller.customer.request.CustomerPageRequest;
import com.design.controller.customer.response.CustomerFindAllResponse;
import com.design.controller.customer.response.CustomerFindResponse;
import com.design.controller.customer.response.CustomerPageResponse;

import java.util.List;

public interface CustomerFindUseCase {

    CustomerFindResponse findDetail(String uuid);

    List<CustomerFindAllResponse> findAll(CustomerFindRequest request);

    CustomerPageResponse findByPage(CustomerPageRequest request);

}
