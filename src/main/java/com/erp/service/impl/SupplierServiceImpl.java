package com.erp.service.impl;

import com.erp.base.response.enums.SupplierCode;
import com.erp.entity.SupplierEntity;
import com.erp.entity.enums.SupplierStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.SupplierRepository;
import com.erp.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierEntity create(SupplierEntity supplierEntity) {
        // 檢查名稱
        supplierRepository.findByIsDeletedFalseAndName(
                supplierEntity.getName()
        ).ifPresent(u -> {
            throw new BusinessException(SupplierCode.DUPLICATE_NAME);
        });
        // 檢查代碼
        supplierRepository.findByIsDeletedFalseAndCode(
                supplierEntity.getCode()
        ).ifPresent(u -> {
            throw new BusinessException(SupplierCode.DUPLICATE_CODE);
        });
        supplierEntity.setStatus(SupplierStatus.ENABLE);
        supplierEntity.setIsDeleted(false);
        supplierEntity.setCreateTime(Instant.now());
        supplierEntity.setCreateUser(UserUtil.getUserUuid());
        supplierEntity.setModifiedTime(Instant.now());
        supplierEntity.setModifiedUser(UserUtil.getUserUuid());
        return supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierEntity edit(SupplierEntity supplierEntity) {
        // 檢查名稱
        supplierRepository.findByIsDeletedFalseAndName(
                supplierEntity.getName()
        ).ifPresent(u -> {
            if(!u.getUuid().equals(supplierEntity.getUuid())){
                throw new BusinessException(SupplierCode.DUPLICATE_NAME);
            }
        });
        // 檢查代碼
        supplierRepository.findByIsDeletedFalseAndCode(
                supplierEntity.getCode()
        ).ifPresent(u -> {
            if(!u.getUuid().equals(supplierEntity.getUuid())){
                throw new BusinessException(SupplierCode.DUPLICATE_CODE);
            }
        });
        supplierEntity.setModifiedTime(Instant.now());
        supplierEntity.setModifiedUser(UserUtil.getUserUuid());
        return supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierEntity delete(UUID uuid) {
        SupplierEntity supplierEntity = findByUuid(uuid);
        supplierEntity.setIsDeleted(true);
        supplierEntity.setDeletedTime(Instant.now());
        supplierEntity.setDeletedUser(UserUtil.getUserUuid());
        return supplierRepository.save(supplierEntity);
    }

    @Override
    public SupplierEntity findByUuid(UUID uuid) {
        return supplierRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(SupplierCode.NOT_EXISTS));
    }

    @Override
    public List<SupplierEntity> findAll(String keyword, SupplierStatus status) {
        return supplierRepository.findAll(keyword, status);
    }

    @Override
    public Page<SupplierEntity> findPage(Pageable pageable, String keyword, SupplierStatus status) {
        return supplierRepository.findPage(pageable, keyword, status);
    }

}
