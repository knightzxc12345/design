package com.design.service;

import com.design.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    void create(ItemEntity itemEntity);

    void edit(ItemEntity itemEntity);

    void delete(ItemEntity itemEntity);

    ItemEntity findByUuid(String uuid);

    List<ItemEntity> findAll(String keyword);

    Page<ItemEntity> findByPage(String keyword, Pageable pageable);

}
