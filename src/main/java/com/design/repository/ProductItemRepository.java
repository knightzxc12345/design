package com.design.repository;

import com.design.entity.ProductItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItemEntity, Long> {

    List<ProductItemEntity> findByIsDeletedFalseAndProduct_Uuid(String uuid);

}
