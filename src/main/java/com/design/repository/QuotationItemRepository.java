package com.design.repository;

import com.design.entity.QuotationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationItemRepository extends JpaRepository<QuotationProductEntity, Long> {



}
