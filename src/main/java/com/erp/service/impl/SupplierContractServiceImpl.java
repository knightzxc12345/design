package com.erp.service.impl;

import com.erp.base.response.enums.SupplierContractCode;
import com.erp.entity.SupplierContractEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.SupplierContractRepository;
import com.erp.service.SupplierContractService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierContractServiceImpl implements SupplierContractService {

    private final SupplierContractRepository supplierContractRepository;

    @Override
    public SupplierContractEntity create(SupplierContractEntity supplierContractEntity) {
        supplierContractEntity.setIsDeleted(false);
        supplierContractEntity.setCreateTime(Instant.now());
        supplierContractEntity.setCreateUser(UserUtil.getUserUuid());
        return supplierContractRepository.save(supplierContractEntity);
    }

    @Override
    public SupplierContractEntity edit(SupplierContractEntity supplierContractEntity) {
        supplierContractEntity.setModifiedTime(Instant.now());
        supplierContractEntity.setModifiedUser(UserUtil.getUserUuid());
        return supplierContractRepository.save(supplierContractEntity);
    }

    @Override
    public SupplierContractEntity delete(UUID uuid) {
        SupplierContractEntity supplierContractEntity = findByUuid(uuid);
        supplierContractEntity.setDeletedTime(Instant.now());
        supplierContractEntity.setDeletedUser(UserUtil.getUserUuid());
        supplierContractEntity.setIsDeleted(true);
        return supplierContractRepository.save(supplierContractEntity);
    }

    @Override
    public void deleteAll(UUID supplierUuid) {
        List<SupplierContractEntity> supplierContractEntities = supplierContractRepository.findByIsDeletedFalseAndSupplierUuid(supplierUuid);
        if(null == supplierContractEntities || supplierContractEntities.isEmpty()){
            return;
        }
        Instant now = Instant.now();
        UUID userUuid = UserUtil.getUserUuid();
        supplierContractEntities.forEach(entity -> {
            entity.setIsDeleted(true);
            entity.setDeletedTime(now);
            entity.setDeletedUser(userUuid);
        });
        supplierContractRepository.saveAll(supplierContractEntities);
    }

    @Override
    public SupplierContractEntity findByUuid(UUID uuid) {
        return supplierContractRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(SupplierContractCode.NOT_EXISTS));
    }

    @Override
    public List<SupplierContractEntity> findAllBySupplierUuid(UUID supplierUuid) {
        return supplierContractRepository.findByIsDeletedFalseAndSupplierUuid(supplierUuid);
    }

}
