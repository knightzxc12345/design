package com.design.service;

import com.design.entity.QuotationProductEntity;

import java.util.List;

public interface QuotationProductService {

    void createAll(List<QuotationProductEntity> quotationProductEntities);

    void deleteAll(List<QuotationProductEntity> quotationProductEntities);

    List<QuotationProductEntity> findByQuotationUuid(String uuid);

}
