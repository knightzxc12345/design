package com.erp.usecase.customer;

import com.erp.controller.customer.request.CustomerEditRequest;

import java.util.UUID;

public interface CustomerEditUseCase {

    void edit(UUID uuid, CustomerEditRequest request);

}
