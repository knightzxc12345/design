package com.design.service.impl;

import com.design.base.api.SupplierCode;
import com.design.entity.SupplierEntity;
import com.design.entity.enums.SupplierStatus;
import com.design.handler.BusinessException;
import com.design.repository.SupplierRepository;
import com.design.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public void create(SupplierEntity supplierEntity) {
        if (supplierRepository.findByNameAndIsDeletedFalse(supplierEntity.getName()).isPresent()) {
            throw new BusinessException(SupplierCode.DUPLICATE_NAME);
        }
        supplierEntity.setStatus(SupplierStatus.ACTIVE);
        supplierEntity.setUuid(UUID.randomUUID().toString());
        supplierEntity.setCreateTime(Instant.now());
        supplierEntity.setCreateUser(UserUtil.getUserUuid());
        supplierEntity.setIsDeleted(false);
        supplierRepository.save(supplierEntity);
    }

    @Override
    public void edit(SupplierEntity supplierEntity) {
        if (supplierRepository.findByNameAndUuidAndIsDeletedFalse(supplierEntity.getName(), supplierEntity.getUuid()).isPresent()) {
            throw new BusinessException(SupplierCode.DUPLICATE_NAME);
        }
        supplierEntity.setModifiedTime(Instant.now());
        supplierEntity.setModifiedUser(UserUtil.getUserUuid());
        supplierRepository.save(supplierEntity);
    }

    @Override
    public void delete(SupplierEntity supplierEntity) {
        supplierEntity.setModifiedTime(Instant.now());
        supplierEntity.setModifiedUser(UserUtil.getUserUuid());
        supplierEntity.setIsDeleted(true);
        supplierEntity.setDeletedTime(Instant.now());
        supplierEntity.setDeletedUser(UserUtil.getUserUuid());
        supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierEntity findByUuid(String uuid) {
        return supplierRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new BusinessException(SupplierCode.NOT_EXISTS));
    }

    @Override
    public List<SupplierEntity> findAll(String keyword) {
        return supplierRepository.findAll(
                keyword
        );
    }

    @Override
    public Page<SupplierEntity> findByPage(String keyword, Pageable pageable) {
        return supplierRepository.findByPage(
                keyword,
                pageable
        );
    }

}
