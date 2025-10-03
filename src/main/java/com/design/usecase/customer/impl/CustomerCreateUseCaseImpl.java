package com.design.usecase.customer.impl;

import com.design.controller.customer.request.CustomerCreateRequest;
import com.design.entity.CustomerEntity;
import com.design.service.CustomerService;
import com.design.usecase.customer.CustomerCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCreateUseCaseImpl implements CustomerCreateUseCase {

    private final CustomerService customerService;

    @Override
    public void create(CustomerCreateRequest request) {
        CustomerEntity customerEntity = init(request);
        customerService.create(customerEntity);
    }

    private CustomerEntity init(CustomerCreateRequest request){
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(request.name());
        customerEntity.setVatNumber(request.vatNumber());
        customerEntity.setPhone(request.phone());
        customerEntity.setFax(request.fax());
        customerEntity.setEmail(request.email());
        customerEntity.setAddress(request.address());
        customerEntity.setContactName(request.contactName());
        customerEntity.setContactPhone(request.contactPhone());
        customerEntity.setRemark(request.remark());
        return customerEntity;
    }

}
