package com.design.service.impl;

import com.design.base.api.ItemCode;
import com.design.entity.ItemEntity;
import com.design.entity.enums.ItemStatus;
import com.design.handler.BusinessException;
import com.design.repository.ItemRepository;
import com.design.service.ItemService;
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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public void create(ItemEntity itemEntity) {
        if (itemRepository.findByCodeAndIsDeletedFalse(itemEntity.getCode()).isPresent()) {
            throw new BusinessException(ItemCode.DUPLICATE_CODE);
        }
        itemEntity.setStatus(ItemStatus.ACTIVE);
        itemEntity.setUuid(UUID.randomUUID().toString());
        itemEntity.setCreateTime(Instant.now());
        itemEntity.setCreateUser(UserUtil.getUserUuid());
        itemEntity.setIsDeleted(false);
        itemRepository.save(itemEntity);
    }

    @Override
    public void edit(ItemEntity itemEntity) {
        if (itemRepository.findByCodeAndUuidNotAndIsDeletedFalse(itemEntity.getCode(), itemEntity.getUuid()).isPresent()) {
            throw new BusinessException(ItemCode.DUPLICATE_CODE);
        }
        itemEntity.setModifiedTime(Instant.now());
        itemEntity.setModifiedUser(UserUtil.getUserUuid());
        itemRepository.save(itemEntity);
    }

    @Override
    public void delete(ItemEntity itemEntity) {
        itemEntity.setModifiedTime(Instant.now());
        itemEntity.setModifiedUser(UserUtil.getUserUuid());
        itemEntity.setIsDeleted(true);
        itemEntity.setDeletedTime(Instant.now());
        itemEntity.setDeletedUser(UserUtil.getUserUuid());
        itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity findByUuid(String uuid) {
        return itemRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new BusinessException(ItemCode.NOT_EXISTS));
    }

    @Override
    public List<ItemEntity> findAllWithUuids(List<String> uuids) {
        return itemRepository.findByIsDeletedFalseAndUuidIn(uuids);
    }

    @Override
    public List<ItemEntity> findAll(String keyword) {
        return itemRepository.findAll(
                keyword
        );
    }

    @Override
    public Page<ItemEntity> findByPage(String keyword, Pageable pageable) {
        return itemRepository.findByPage(
                keyword,
                pageable
        );
    }

}
