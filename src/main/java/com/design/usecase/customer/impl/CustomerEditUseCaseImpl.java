package com.design.usecase.customer.impl;

import com.design.controller.customer.request.CustomerEditRequest;
import com.design.entity.CustomerEntity;
import com.design.service.CustomerService;
import com.design.usecase.customer.CustomerEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerEditUseCaseImpl implements CustomerEditUseCase {

    private final CustomerService customerService;

    @Transactional
    @Override
    public void edit(String uuid, CustomerEditRequest request) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        customerEntity = init(customerEntity, request);
        customerService.edit(customerEntity);
    }

    private CustomerEntity init(CustomerEntity customerEntity, CustomerEditRequest request){
        customerEntity.setName(request.name());
        customerEntity.setVatNumber(request.vatNumber());
        customerEntity.setPhone(request.phone());
        customerEntity.setFax(request.fax());
        customerEntity.setEmail(request.email());
        customerEntity.setAddress(request.address());
        customerEntity.setContactName(request.contactName());
        customerEntity.setContactPhone(request.contactPhone());
        customerEntity.setRemark(request.remark());
        customerEntity.setStatus(request.status());
        return customerEntity;
    }

}
