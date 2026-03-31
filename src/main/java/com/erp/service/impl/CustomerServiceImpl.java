package com.erp.service.impl;

import com.erp.base.response.enums.CustomerCode;
import com.erp.entity.CustomerEntity;
import com.erp.entity.enums.CustomerStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.CustomerRepository;
import com.erp.service.CustomerService;
import com.erp.utils.UserUtil;
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
    public CustomerEntity create(CustomerEntity customerEntity) {
        // 檢查姓名
        customerRepository.findByIsDeletedFalseAndName(
                customerEntity.getName()
        ).ifPresent(c ->{
                    throw new BusinessException(CustomerCode.DUPLICATE_NAME);
        });
        // 檢查信箱
        customerRepository.findByIsDeletedFalseAndEmail(
                customerEntity.getEmail()
        ).ifPresent(c ->{
            throw new BusinessException(CustomerCode.DUPLICATE_EMAIL);
        });
        customerEntity.setStatus(CustomerStatus.ENABLE);
        customerEntity.setCreateTime(Instant.now());
        customerEntity.setCreateUser(UserUtil.getUserUuid());
        customerEntity.setIsDeleted(false);
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity edit(CustomerEntity customerEntity) {
        // 檢查姓名
        customerRepository.findByIsDeletedFalseAndName(
                customerEntity.getName()
        ).ifPresent(c ->{
            if(!c.getUuid().equals(customerEntity.getUuid())){
                throw new BusinessException(CustomerCode.DUPLICATE_NAME);
            }
        });
        // 檢查信箱
        customerRepository.findByIsDeletedFalseAndEmail(
                customerEntity.getEmail()
        ).ifPresent(c ->{
            if(!c.getUuid().equals(customerEntity.getUuid())){
                throw new BusinessException(CustomerCode.DUPLICATE_EMAIL);
            }
        });
        customerEntity.setModifiedTime(Instant.now());
        customerEntity.setModifiedUser(UserUtil.getUserUuid());
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity delete(UUID uuid) {
        CustomerEntity customerEntity = findByUuid(uuid);
        customerEntity.setModifiedTime(Instant.now());
        customerEntity.setModifiedUser(UserUtil.getUserUuid());
        customerEntity.setIsDeleted(true);
        customerEntity.setDeletedTime(Instant.now());
        customerEntity.setDeletedUser(UserUtil.getUserUuid());
        return customerRepository.save(customerEntity);
    }

    @Override
    public CustomerEntity findByUuid(UUID uuid) {
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
    public Page<CustomerEntity> findByPage(Pageable pageable, String keyword) {
        return customerRepository.findByPage(
                pageable,
                keyword
        );
    }

}
