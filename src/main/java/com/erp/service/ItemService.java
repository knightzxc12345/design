package com.erp.service;

import com.erp.entity.ItemEntity;
import com.erp.entity.enums.ItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    ItemEntity create(ItemEntity itemEntity);

    ItemEntity edit(ItemEntity itemEntity);

    ItemEntity delete(UUID uuid);

    ItemEntity findByUuid(UUID uuid);

    List<ItemEntity> findAllByProductUuid(
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus
    );

    Page<ItemEntity> findPageByProductUuid(
            Pageable pageable,
            UUID productUuid,
            String keyword,
            ItemStatus itemStatus
    );

}
