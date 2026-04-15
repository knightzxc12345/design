package com.erp.service.impl;

import com.erp.base.response.enums.BrandCode;
import com.erp.entity.BrandEntity;
import com.erp.entity.enums.BrandStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.BrandRepository;
import com.erp.service.BrandService;
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
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public BrandEntity create(BrandEntity brandEntity) {
        // 檢查名稱
        brandRepository.findByIsDeletedFalseAndName(
                brandEntity.getName()
        ).ifPresent(c -> {
            throw new BusinessException(BrandCode.DUPLICATE_NAME);
        });
        // 檢查代碼
        brandRepository.findByIsDeletedFalseAndCode(
                brandEntity.getCode()
        ).ifPresent(c -> {
            throw new BusinessException(BrandCode.DUPLICATE_CODE);
        });
        brandEntity.setStatus(BrandStatus.ENABLE);
        brandEntity.setDeletedTime(Instant.now());
        brandEntity.setDeletedUser(UserUtil.getUserUuid());
        brandEntity.setIsDeleted(false);
        return brandRepository.save(brandEntity);
    }

    @Override
    public BrandEntity edit(BrandEntity brandEntity) {
        // 檢查名稱
        brandRepository.findByIsDeletedFalseAndName(
                brandEntity.getName()
        ).ifPresent(c -> {
            if(!c.getUuid().equals(brandEntity.getUuid())){
                throw new BusinessException(BrandCode.DUPLICATE_NAME);
            }
        });
        // 檢查代碼
        brandRepository.findByIsDeletedFalseAndCode(
                brandEntity.getCode()
        ).ifPresent(c -> {
            if(!c.getUuid().equals(brandEntity.getUuid())){
                throw new BusinessException(BrandCode.DUPLICATE_CODE);
            }
        });
        brandEntity.setModifiedTime(Instant.now());
        brandEntity.setModifiedUser(UserUtil.getUserUuid());
        return brandRepository.save(brandEntity);
    }

    @Override
    public BrandEntity delete(UUID uuid) {
        BrandEntity brandEntity = findByUuid(uuid);
        brandEntity.setIsDeleted(true);
        brandEntity.setDeletedTime(Instant.now());
        brandEntity.setDeletedUser(UserUtil.getUserUuid());
        return brandRepository.save(brandEntity);
    }

    @Override
    public BrandEntity findByUuid(UUID uuid) {
        return brandRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(BrandCode.NOT_EXISTS));
    }

    @Override
    public List<BrandEntity> findAll(String keyword, BrandStatus brandStatus) {
        return brandRepository.findAll(
                keyword,
                brandStatus
        );
    }

    @Override
    public Page<BrandEntity> findByPage(Pageable pageable, String keyword, BrandStatus brandStatus) {
        return brandRepository.findByPage(
                pageable,
                keyword,
                brandStatus
        );
    }

}
