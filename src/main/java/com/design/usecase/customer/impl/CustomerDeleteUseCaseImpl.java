package com.design.usecase.customer.impl;

import com.design.entity.CustomerEntity;
import com.design.service.CustomerService;
import com.design.usecase.customer.CustomerDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerDeleteUseCaseImpl implements CustomerDeleteUseCase {

    private final CustomerService customerService;

    @Transactional
    @Override
    public void delete(String uuid) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        customerService.delete(customerEntity);
    }

}
