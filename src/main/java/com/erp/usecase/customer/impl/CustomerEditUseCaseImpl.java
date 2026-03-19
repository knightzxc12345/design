package com.erp.usecase.customer.impl;

import com.erp.controller.customer.request.CustomerEditRequest;
import com.erp.entity.CustomerEntity;
import com.erp.service.CustomerService;
import com.erp.usecase.customer.CustomerEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerEditUseCaseImpl implements CustomerEditUseCase {

    private final CustomerService customerService;

    @Transactional
    @Override
    public void edit(UUID uuid, CustomerEditRequest request) {
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
