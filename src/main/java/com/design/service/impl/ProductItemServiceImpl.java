package com.design.service.impl;

import com.design.entity.ProductItemEntity;
import com.design.repository.ProductItemRepository;
import com.design.service.ProductItemService;
import com.design.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

    private final ProductItemRepository productItemRepository;

    @Override
    public void createAll(List<ProductItemEntity> productItemEntities) {
        if(null == productItemEntities || productItemEntities.isEmpty()){
            return;
        }
        for(ProductItemEntity productItemEntity : productItemEntities){
            productItemEntity.setUuid(UUID.randomUUID().toString());
            productItemEntity.setIsDeleted(false);
            productItemEntity.setCreateTime(Instant.now());
            productItemEntity.setCreateUser(UserUtil.getUserUuid());
        }
        productItemRepository.saveAll(productItemEntities);
    }

    @Override
    public void deleteAll(List<ProductItemEntity> productItemEntities) {
        if(null == productItemEntities || productItemEntities.isEmpty()){
            return;
        }
        for(ProductItemEntity productItemEntity : productItemEntities){
            productItemEntity.setIsDeleted(true);
            productItemEntity.setDeletedTime(Instant.now());
            productItemEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        productItemRepository.saveAll(productItemEntities);
    }

    @Override
    public List<ProductItemEntity> findAll(String productUuid) {
        return productItemRepository.findByIsDeletedFalseAndProduct_Uuid(productUuid);
    }

}
