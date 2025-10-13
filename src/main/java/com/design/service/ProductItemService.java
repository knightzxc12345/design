package com.design.service;

import com.design.entity.ProductItemEntity;

import java.util.List;

public interface ProductItemService {

    void createAll(List<ProductItemEntity> productItemEntities);

    void deleteAll(List<ProductItemEntity> productItemEntities);

    List<ProductItemEntity> findAll(String productUuid);

}
