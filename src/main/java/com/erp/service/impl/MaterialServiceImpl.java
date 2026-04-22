package com.erp.service.impl;

import com.erp.base.response.enums.MaterialCode;
import com.erp.entity.MaterialEntity;
import com.erp.entity.enums.MaterialStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.MaterialRepository;
import com.erp.service.MaterialService;
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
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    public MaterialEntity create(MaterialEntity materialEntity) {
        materialEntity.setStatus(MaterialStatus.ENABLE);
        materialEntity.setIsDeleted(false);
        materialEntity.setCreateTime(Instant.now());
        materialEntity.setCreateUser(UserUtil.getUserUuid());
        return materialRepository.save(materialEntity);
    }

    @Override
    public MaterialEntity edit(MaterialEntity materialEntity) {
        materialEntity.setModifiedTime(Instant.now());
        materialEntity.setModifiedUser(UserUtil.getUserUuid());
        return materialRepository.save(materialEntity);
    }

    @Override
    public MaterialEntity delete(UUID uuid) {
        MaterialEntity materialEntity = findByUuid(uuid);
        materialEntity.setIsDeleted(true);
        materialEntity.setDeletedTime(Instant.now());
        materialEntity.setDeletedUser(UserUtil.getUserUuid());
        return materialRepository.save(materialEntity);
    }

    @Override
    public MaterialEntity findByUuid(UUID uuid) {
        return materialRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(MaterialCode.NOT_EXISTS));
    }

    @Override
    public List<MaterialEntity> findAll(
            UUID supplierUuid,
            String keyword,
            MaterialStatus materialStatus) {
        return materialRepository.findAll(
                supplierUuid,
                keyword,
                materialStatus
        );
    }

    @Override
    public Page<MaterialEntity> findPage(
            Pageable pageable,
            UUID supplierUuid,
            String keyword,
            MaterialStatus materialStatus) {
        return materialRepository.findPage(
                pageable,
                supplierUuid,
                keyword,
                materialStatus
        );
    }

}
