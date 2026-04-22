package com.erp.service.impl;

import com.erp.base.response.enums.BomItemCode;
import com.erp.entity.BomItemEntity;
import com.erp.handler.BusinessException;
import com.erp.repository.BomItemRepository;
import com.erp.service.BomItemService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BomItemServiceImpl implements BomItemService {

    private final BomItemRepository bomItemRepository;

    @Override
    public BomItemEntity create(BomItemEntity bomItemEntity) {
        bomItemRepository.findByIsDeletedFalseAndBomUuidAndMaterialUuid(
                bomItemEntity.getBomUuid(),
                bomItemEntity.getMaterialUuid()
        ).ifPresent(b -> {
            throw new BusinessException(BomItemCode.DUPLICATE_MATERIAL);
        });
        bomItemEntity.setIsDeleted(false);
        bomItemEntity.setCreateTime(Instant.now());
        bomItemEntity.setCreateUser(UserUtil.getUserUuid());
        return bomItemRepository.save(bomItemEntity);
    }

    @Override
    public BomItemEntity edit(BomItemEntity bomItemEntity) {
        bomItemRepository.findByIsDeletedFalseAndBomUuidAndMaterialUuid(
                bomItemEntity.getBomUuid(),
                bomItemEntity.getMaterialUuid()
        ).ifPresent(b -> {
            if(!b.getUuid().equals(bomItemEntity.getUuid())){
                throw new BusinessException(BomItemCode.DUPLICATE_MATERIAL);
            }
        });
        bomItemEntity.setModifiedTime(Instant.now());
        bomItemEntity.setModifiedUser(UserUtil.getUserUuid());
        return bomItemRepository.save(bomItemEntity);
    }

    @Override
    public BomItemEntity delete(UUID uuid) {
        BomItemEntity bomItemEntity = findByUuid(uuid);
        bomItemEntity.setIsDeleted(true);
        bomItemEntity.setDeletedTime(Instant.now());
        bomItemEntity.setDeletedUser(UserUtil.getUserUuid());
        return bomItemRepository.save(bomItemEntity);
    }

    @Override
    public void deleteByBomUuid(UUID bomUuid) {
        List<BomItemEntity> bomItemEntities = bomItemRepository.findByIsDeletedFalseAndBomUuid(bomUuid);
        if(null == bomItemEntities || bomItemEntities.isEmpty()){
            return;
        }
        for(BomItemEntity bomItemEntity : bomItemEntities){
            bomItemEntity.setIsDeleted(true);
            bomItemEntity.setDeletedTime(Instant.now());
            bomItemEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        bomItemRepository.saveAll(bomItemEntities);
    }

    @Override
    public BomItemEntity findByUuid(UUID uuid) {
        return bomItemRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(BomItemCode.NOT_EXISTS));
    }

    @Override
    public List<BomItemEntity> findAll(UUID bomUuid) {
        return bomItemRepository.findByIsDeletedFalseAndBomUuid(bomUuid);
    }

}
