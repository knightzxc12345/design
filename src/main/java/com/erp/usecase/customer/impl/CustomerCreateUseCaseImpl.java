package com.erp.usecase.customer.impl;

import com.erp.controller.customer.request.CustomerCreateRequest;
import com.erp.entity.CustomerEntity;
import com.erp.service.CustomerService;
import com.erp.usecase.customer.CustomerCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerCreateUseCaseImpl implements CustomerCreateUseCase {

    private final CustomerService customerService;

    @Transactional
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
