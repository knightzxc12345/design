package com.design.service.impl;

import com.design.base.api.CustomerCode;
import com.design.base.api.SupplierCode;
import com.design.entity.CustomerEntity;
import com.design.entity.enums.CustomerStatus;
import com.design.handler.BusinessException;
import com.design.repository.CustomerRepository;
import com.design.service.CustomerService;
import com.design.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public void create(CustomerEntity customerEntity) {
        if (customerRepository.findByNameAndIsDeletedFalse(customerEntity.getName()).isPresent()) {
            throw new BusinessException(CustomerCode.DUPLICATE_NAME);
        }
        customerEntity.setStatus(CustomerStatus.ACTIVE);
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setCreateTime(Instant.now());
        customerEntity.setCreateUser(UserUtil.getUserUuid());
        customerEntity.setIsDeleted(false);
        customerRepository.save(customerEntity);
    }

    @Override
    public void edit(CustomerEntity customerEntity) {
        if (customerRepository.findByNameAndUuidNotAndIsDeletedFalse(customerEntity.getName(), customerEntity.getUuid()).isPresent()) {
            throw new BusinessException(SupplierCode.DUPLICATE_NAME);
        }
        customerEntity.setModifiedTime(Instant.now());
        customerEntity.setModifiedUser(UserUtil.getUserUuid());
        customerRepository.save(customerEntity);
    }

    @Override
    public void delete(CustomerEntity customerEntity) {
        customerEntity.setModifiedTime(Instant.now());
        customerEntity.setModifiedUser(UserUtil.getUserUuid());
        customerEntity.setIsDeleted(true);
        customerEntity.setDeletedTime(Instant.now());
        customerEntity.setDeletedUser(UserUtil.getUserUuid());
        customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity findByUuid(String uuid) {
        return customerRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new BusinessException(CustomerCode.NOT_EXISTS));
    }

    @Override
    public List<CustomerEntity> findAll(String keyword) {
        return customerRepository.findAll(
                keyword
        );
    }

    @Override
    public Page<CustomerEntity> findByPage(String keyword, Pageable pageable) {
        return customerRepository.findByPage(
                keyword,
                pageable
        );
    }

}
