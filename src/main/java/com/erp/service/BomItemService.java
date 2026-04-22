package com.erp.service;

import com.erp.entity.BomItemEntity;

import java.util.List;
import java.util.UUID;

public interface BomItemService {

    BomItemEntity create(BomItemEntity bomItemEntity);

    BomItemEntity edit(BomItemEntity bomItemEntity);

    BomItemEntity delete(UUID uuid);

    void deleteByBomUuid(UUID bomUuid);

    BomItemEntity findByUuid(UUID uuid);

    List<BomItemEntity> findAll(UUID bomUuid);

}
