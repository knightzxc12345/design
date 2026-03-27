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
        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(request.name())
                .vatNumber(request.vatNumber())
                .phone(request.phone())
                .fax(request.fax())
                .email(request.email())
                .address(request.address())
                .contactName(request.contactName())
                .contactPhone(request.contactPhone())
                .remark(request.remark())
                .build();
        customerService.create(customerEntity);
    }

}
