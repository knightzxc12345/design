package com.design.usecase.quotation.impl;

import com.design.base.common.Common;
import com.design.controller.quotation.request.QuotationCreateRequest;
import com.design.entity.CustomerEntity;
import com.design.entity.ProductEntity;
import com.design.entity.QuotationEntity;
import com.design.entity.QuotationProductEntity;
import com.design.service.CustomerService;
import com.design.service.ProductService;
import com.design.service.QuotationProductService;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationCreateUseCase;
import com.design.utils.InstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationCreateUseCaseImpl implements QuotationCreateUseCase {

    private final QuotationService quotationService;

    private final QuotationProductService quotationProductService;

    private final CustomerService customerService;

    private final ProductService productService;

    @Override
    public void create(QuotationCreateRequest request) {
        // 1. 建立 quotation 並存入資料庫 (先生成 UUID/PK)
        QuotationEntity quotationEntity = init(request);
        quotationService.create(quotationEntity);

        // 2.取得產品清單
        List<String> productUuids = request.products().stream()
                .map(QuotationCreateRequest.Product::productUuid)
                .toList();
        List<ProductEntity> products = productService.findByUuids(productUuids);
        Map<String, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getUuid, p -> p));

        // 3. 建立報價明細 list
        List<QuotationProductEntity> quotationProductEntities = initProducts(request, quotationEntity, productMap);

        // 4. 存入明細
        quotationProductService.createAll(quotationProductEntities);

        // 5. 補回雙向關聯到 quotation
        quotationEntity.setProducts(quotationProductEntities);
        quotationService.edit(quotationEntity);
    }

    private QuotationEntity init(QuotationCreateRequest request) {
        CustomerEntity customerEntity = customerService.findByUuid(request.customerUuid());
        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity.setQuotationNo(String.format("%s%s", "DE", InstantUtil.to(Instant.now(), Common.DATE_FORMAT_3)));
        quotationEntity.setCustomer(customerEntity);
        return quotationEntity;
    }

    private List<QuotationProductEntity> initProducts(
            QuotationCreateRequest request,
            QuotationEntity quotation, Map<String,
            ProductEntity> productMap) {
        return request.products().stream().map(p -> {
            ProductEntity product = productMap.get(p.productUuid());
            if (product == null) {
                throw new RuntimeException("產品不存在: " + p.productUuid());
            }
            QuotationProductEntity qpe = new QuotationProductEntity();
            qpe.setQuotation(quotation);
            qpe.setProduct(product);
            qpe.setQuantity(p.quantity());
            qpe.setNegotiatedPrice(p.negotiatedPrice());
            return qpe;
        }).toList();
    }

    private Map<String, BigDecimal> calTotalCostPrice(List<QuotationProductEntity> quotationProductEntities){
        BigDecimal totalCostPrice = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);
        BigDecimal totalNegotiatedPrice = new BigDecimal(0);
        Map<String, BigDecimal> priceMap = new HashMap<>();
        for(QuotationProductEntity quotationProductEntity : quotationProductEntities){

        }
        return priceMap;
    }

}
