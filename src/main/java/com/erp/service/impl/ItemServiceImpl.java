package com.erp.service.impl;

import com.erp.base.response.enums.CategoryCode;
import com.erp.base.response.enums.ItemCode;
import com.erp.entity.ItemEntity;
import com.erp.entity.enums.ItemStatus;
import com.erp.handler.BusinessException;
import com.erp.repository.ItemRepository;
import com.erp.service.ItemService;
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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemEntity create(ItemEntity itemEntity) {
        itemRepository.findByIsDeletedFalseAndProductUuidAndSkuCode(
                itemEntity.getProductUuid(), itemEntity.getSkuCode()
        ).ifPresent(i -> {
            throw new BusinessException(ItemCode.DUPLICATE_SKU_CODE);
        });
        itemEntity.setStatus(ItemStatus.ENABLE);
        itemEntity.setIsDeleted(false);
        itemEntity.setDeletedTime(Instant.now());
        itemEntity.setDeletedUser(UserUtil.getUserUuid());
        return itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity edit(ItemEntity itemEntity) {
        itemRepository.findByIsDeletedFalseAndProductUuidAndSkuCode(
                itemEntity.getProductUuid(), itemEntity.getSkuCode()
        ).ifPresent(i -> {
            if(!i.getUuid().equals(itemEntity.getUuid())){
                throw new BusinessException(ItemCode.DUPLICATE_SKU_CODE);
            }
        });
        itemEntity.setModifiedTime(Instant.now());
        itemEntity.setModifiedUser(UserUtil.getUserUuid());
        return itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity delete(UUID uuid) {
        ItemEntity itemEntity = findByUuid(uuid);
        itemEntity.setIsDeleted(true);
        itemEntity.setDeletedTime(Instant.now());
        itemEntity.setDeletedUser(UserUtil.getUserUuid());
        return itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity findByUuid(UUID uuid) {
        return itemRepository.findByIsDeletedFalseAndUuid(uuid)
                .orElseThrow(() -> new BusinessException(ItemCode.NOT_EXISTS));
    }

    @Override
    public List<ItemEntity> findAllByProductUuid(
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus) {
        return itemRepository.findAll(
                productUuid,
                keyword,
                itemStatus
        );
    }

    @Override
    public Page<ItemEntity> findPageByProductUuid(
            Pageable pageable,
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus) {
        return itemRepository.findPage(
                pageable,
                productUuid,
                keyword,
                itemStatus
        );
    }

}
