package com.design.usecase.customer;

import com.design.controller.customer.request.CustomerEditRequest;

public interface CustomerEditUseCase {

    void edit(String uuid, CustomerEditRequest request);

}
