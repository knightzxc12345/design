package com.design.service;

import com.design.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    void create(ProductEntity productEntity);

    void edit(ProductEntity productEntity);

    void delete(ProductEntity productEntity);

    ProductEntity findByUuid(UUID uuid);

    List<ProductEntity> findAll(String keyword);

    Page<ProductEntity> findByPage(String keyword, Pageable pageable);

}
