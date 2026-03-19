package com.erp.usecase.customer.impl;

import com.erp.entity.CustomerEntity;
import com.erp.service.CustomerService;
import com.erp.usecase.customer.CustomerDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerDeleteUseCaseImpl implements CustomerDeleteUseCase {

    private final CustomerService customerService;

    @Transactional
    @Override
    public void delete(UUID uuid) {
        CustomerEntity customerEntity = customerService.findByUuid(uuid);
        customerService.delete(customerEntity);
    }

}
