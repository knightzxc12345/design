package com.design.service;

import com.design.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemService {

    void create(ItemEntity itemEntity);

    void edit(ItemEntity itemEntity);

    void delete(ItemEntity itemEntity);

    ItemEntity findByUuid(String uuid);

    List<ItemEntity> findAllWithUuids(List<String> uuids);

    List<ItemEntity> findAll(
            String keyword,
            String supplierUuid
    );

    Page<ItemEntity> findByPage(
            String keyword,
            String supplierUuid,
            Pageable pageable
    );

}
