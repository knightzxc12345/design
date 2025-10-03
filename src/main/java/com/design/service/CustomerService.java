package com.design.service;

import com.design.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    void create(CustomerEntity customerEntity);

    void edit(CustomerEntity customerEntity);

    void delete(CustomerEntity customerEntity);

    CustomerEntity findByUuid(String uuid);

    List<CustomerEntity> findAll(String keyword);

    Page<CustomerEntity> findByPage(String keyword, Pageable pageable);

}
