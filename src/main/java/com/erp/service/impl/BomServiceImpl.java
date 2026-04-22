package com.erp.service.impl;

import com.erp.base.response.enums.BomCode;
import com.erp.entity.BomEntity;
import com.erp.entity.enums.BomStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.BomRepository;
import com.erp.service.BomService;
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
public class BomServiceImpl implements BomService {

    private final BomRepository bomRepository;

    @Override
    public BomEntity create(BomEntity bomEntity) {
        bomRepository.findByIsDeletedFalseAndItemUuidAndVersion(
                bomEntity.getItemUuid(),
                bomEntity.getVersion()
        ).ifPresent(b -> {
            throw new BusinessException(BomCode.DUPLICATE_VERSION);
        });
        bomEntity.setStatus(BomStatus.ENABLE);
        bomEntity.setIsDeleted(false);
        bomEntity.setCreateTime(Instant.now());
        bomEntity.setCreateUser(UserUtil.getUserUuid());
        bomEntity.setModifiedTime(Instant.now());
        bomEntity.setModifiedUser(UserUtil.getUserUuid());
        return bomRepository.save(bomEntity);
    }

    @Override
    public BomEntity edit(BomEntity bomEntity) {
        bomRepository.findByIsDeletedFalseAndItemUuidAndVersion(
                bomEntity.getItemUuid(),
                bomEntity.getVersion()
        ).ifPresent(b -> {
            if(!b.getUuid().equals(bomEntity.getUuid())){
                throw new BusinessException(BomCode.DUPLICATE_VERSION);
            }
        });
        bomEntity.setModifiedTime(Instant.now());
        bomEntity.setModifiedUser(UserUtil.getUserUuid());
        return bomRepository.save(bomEntity);
    }

    @Override
    public BomEntity delete(UUID uuid) {
        BomEntity bomEntity = findByUuid(uuid);
        bomEntity.setIsDeleted(true);
        bomEntity.setDeletedTime(Instant.now());
        bomEntity.setDeletedUser(UserUtil.getUserUuid());
        return bomRepository.save(bomEntity);
    }

    @Override
    public BomEntity findByUuid(UUID uuid) {
        return bomRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(BomCode.NOT_EXISTS));
    }

    @Override
    public List<BomEntity> findAll(
            UUID itemUuid,
            String keyword,
            BomStatus bomStatus) {
        return bomRepository.findAll(
                itemUuid,
                keyword,
                bomStatus
        );
    }

    @Override
    public Page<BomEntity> findPage(
            Pageable pageable,
            UUID itemUuid,
            String keyword,
            BomStatus bomStatus) {
        return bomRepository.findPage(
                pageable,
                itemUuid,
                keyword,
                bomStatus
        );
    }

}
