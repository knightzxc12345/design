package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationEditRequest;
import com.design.entity.CustomerEntity;
import com.design.entity.ProductEntity;
import com.design.entity.QuotationEntity;
import com.design.entity.QuotationProductEntity;
import com.design.service.CustomerService;
import com.design.service.ProductService;
import com.design.service.QuotationProductService;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationEditUseCase;
import com.design.model.PriceSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationEditUseCaseImpl implements QuotationEditUseCase {

    private final QuotationService quotationService;

    private final QuotationProductService quotationProductService;

    private final CustomerService customerService;

    private final ProductService productService;

    @Transactional
    @Override
    public void edit(String uuid, QuotationEditRequest request) {
        // 取得報價單
        QuotationEntity quotationEntity = quotationService.findByUuid(uuid);

        // 取得舊明細
        List<QuotationProductEntity> oldProducts = quotationProductService.findByQuotationUuid(quotationEntity.getUuid());

        // 取得產品清單
        List<String> productUuids = request.products().stream()
                .map(QuotationEditRequest.Product::productUuid)
                .toList();
        List<ProductEntity> products = productService.findByUuids(productUuids);
        Map<String, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getUuid, p -> p));

        // 建立新明細
        List<QuotationProductEntity> newProducts = request.products().stream().map(p -> {
            ProductEntity prod = productMap.get(p.productUuid());
            QuotationProductEntity qpe = new QuotationProductEntity();
            qpe.setQuotation(quotationEntity);
            qpe.setProduct(prod);
            qpe.setQuantity(p.quantity());
            qpe.setNegotiatedPrice(prod.getPrice());
            return qpe;
        }).toList();

        // 先刪除舊明細
        quotationProductService.deleteAll(oldProducts);

        // 批量新增新明細
        quotationProductService.createAll(newProducts);

        // 更新報價單基本資訊
        update(quotationEntity, request);

        // 計算金額
        PriceSummary priceSummary = calTotalCostPrice(newProducts, productMap);
        quotationEntity.setTotalCostPrice(priceSummary.totalCostPrice());
        quotationEntity.setTotalPrice(priceSummary.totalPrice());
        quotationEntity.setTotalNegotiatedPrice(priceSummary.totalNegotiatedPrice());

        // 儲存報價單
        quotationService.edit(quotationEntity);
    }

    private QuotationEntity update(QuotationEntity quotationEntity, QuotationEditRequest request) {
        CustomerEntity customer = customerService.findByUuid(request.customerUuid());
        quotationEntity.setCustomer(customer);
        quotationEntity.setRemark(request.remark());
        return quotationEntity;
    }

    private PriceSummary calTotalCostPrice(
            List<QuotationProductEntity> quotationProductEntities,
            Map<String, ProductEntity> productMap) {

        BigDecimal totalCostPrice = quotationProductEntities.stream()
                .map(qp -> {
                    ProductEntity p = productMap.get(qp.getProduct().getUuid());
                    return p == null ? BigDecimal.ZERO :
                            BigDecimal.valueOf(qp.getQuantity())
                                    .multiply(p.getCostPrice() != null ? p.getCostPrice() : BigDecimal.ZERO);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPrice = quotationProductEntities.stream()
                .map(qp -> {
                    ProductEntity p = productMap.get(qp.getProduct().getUuid());
                    return p == null ? BigDecimal.ZERO :
                            BigDecimal.valueOf(qp.getQuantity())
                                    .multiply(p.getPrice() != null ? p.getPrice() : BigDecimal.ZERO);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalNegotiatedPrice = quotationProductEntities.stream()
                .map(qp -> BigDecimal.valueOf(qp.getQuantity())
                        .multiply(qp.getNegotiatedPrice() != null ? qp.getNegotiatedPrice() : BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PriceSummary(totalCostPrice, totalPrice, totalNegotiatedPrice);
    }

}
