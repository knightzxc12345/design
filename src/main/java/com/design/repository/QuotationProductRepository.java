package com.design.repository;

import com.design.entity.QuotationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationProductRepository extends JpaRepository<QuotationProductEntity, Long> {

    List<QuotationProductEntity> findByIsDeletedFalseAndQuotation_Uuid(String uuid);

}
