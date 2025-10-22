package com.design.service.impl;

import com.design.entity.QuotationProductEntity;
import com.design.repository.QuotationProductRepository;
import com.design.service.QuotationProductService;
import com.design.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuotationProductServiceImpl implements QuotationProductService {

    private final QuotationProductRepository quotationProductRepository;

    @Override
    public void createAll(List<QuotationProductEntity> quotationProductEntities) {
        if(null == quotationProductEntities || quotationProductEntities.isEmpty()){
            return;
        }
        for(QuotationProductEntity quotationProductEntity : quotationProductEntities){
            quotationProductEntity.setUuid(UUID.randomUUID().toString());
            quotationProductEntity.setCreateTime(Instant.now());
            quotationProductEntity.setCreateUser(UserUtil.getUserUuid());
            quotationProductEntity.setIsDeleted(false);
        }
        quotationProductRepository.saveAll(quotationProductEntities);
    }

    @Override
    public void deleteAll(List<QuotationProductEntity> quotationProductEntities) {
        if(null == quotationProductEntities || quotationProductEntities.isEmpty()){
            return;
        }
        for(QuotationProductEntity quotationProductEntity : quotationProductEntities){
            quotationProductEntity.setIsDeleted(true);
            quotationProductEntity.setDeletedTime(Instant.now());
            quotationProductEntity.setDeletedUser(UserUtil.getUserUuid());
        }
        quotationProductRepository.saveAll(quotationProductEntities);
    }

    @Override
    public List<QuotationProductEntity> findByQuotationUuid(String uuid) {
        return quotationProductRepository.findByIsDeletedFalseAndQuotation_Uuid(uuid);
    }

}
