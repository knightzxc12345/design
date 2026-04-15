package com.erp.service;

import com.erp.entity.CustomerEntity;
import com.erp.entity.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerEntity create(CustomerEntity customerEntity);

    CustomerEntity edit(CustomerEntity customerEntity);

    CustomerEntity delete(UUID uuid);

    CustomerEntity findByUuid(UUID uuid);

    List<CustomerEntity> findAll(
            String keyword,
            CustomerStatus status
    );

    Page<CustomerEntity> findPage(
            Pageable pageable,
            String keyword,
            CustomerStatus status
    );

}
