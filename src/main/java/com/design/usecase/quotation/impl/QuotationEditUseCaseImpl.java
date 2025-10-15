package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationEditRequest;
import com.design.entity.QuotationEntity;
import com.design.entity.QuotationProductEntity;
import com.design.service.CustomerService;
import com.design.service.ProductService;
import com.design.service.QuotationProductService;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationEditUseCaseImpl implements QuotationEditUseCase {

    private final QuotationService quotationService;

    private final QuotationProductService quotationProductService;

    private final CustomerService customerService;

    private final ProductService productService;

    @Override
    public void edit(String uuid, QuotationEditRequest request) {
        // 1. 取得報價單
        QuotationEntity quotationEntity = quotationService.findByUuid(uuid);
        // 2. 取得報價單明細
        List<QuotationProductEntity> quotationProductEntities = quotationProductService.findByQuotationUuid(quotationEntity.getUuid());
    }

    private QuotationEntity init(QuotationEntity quotationEntity, QuotationEditRequest request){
        return quotationEntity;
    }

}
