package com.design.usecase.customer.impl;

import com.design.entity.CustomerEntity;
import com.design.service.CustomerService;
import com.design.usecase.customer.CustomerDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDeleteUseCaseImpl implements CustomerDeleteUseCase {

    private final CustomerService customerService;

    @Override
    public void delete(String uuid) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        customerService.delete(customerEntity);
    }

}
