package com.erp.service.impl;

import com.erp.entity.ItemEntity;
import com.erp.entity.enums.ItemStatus;
import com.erp.repository.ItemRepository;
import com.erp.service.ItemService;
import com.erp.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        itemEntity.setStatus(ItemStatus.ENABLE);
        itemEntity.setIsDeleted(false);
        itemEntity.setDeletedTime(Instant.now());
        itemEntity.setDeletedUser(UserUtil.getUserUuid());
        return itemRepository.save(itemEntity);
    }

    @Override
    public ItemEntity edit(ItemEntity itemEntity) {
        return null;
    }

    @Override
    public ItemEntity delete(UUID uuid) {
        return null;
    }

    @Override
    public ItemEntity findByUuid(UUID uuid) {
        return null;
    }

    @Override
    public List<ItemEntity> findAllByProductUuid(
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus) {
        return null;
    }

    @Override
    public Page<ItemEntity> findPageByProductUuid(
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus) {
        return null;
    }

}
