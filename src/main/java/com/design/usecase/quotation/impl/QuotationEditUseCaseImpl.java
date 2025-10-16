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
import com.design.usecase.quotation.model.PriceSummary;
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

        // 更新報價單基本資訊（例如客戶、備註等）
        init(quotationEntity, request);

        // 取得原有明細
        List<QuotationProductEntity> existingProducts =
                quotationProductService.findByQuotationUuid(quotationEntity.getUuid());

        // 先刪除舊的明細（也可以選擇比較差異後再刪/改/新增）
        quotationProductService.deleteAll(existingProducts);

        // 取得產品清單
        List<String> productUuids = request.products().stream()
                .map(QuotationEditRequest.Product::productUuid)
                .toList();
        List<ProductEntity> products = productService.findByUuids(productUuids);
        Map<String, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getUuid, p -> p));

        // 立新的報價明細
        List<QuotationProductEntity> newQuotationProducts =
                initProducts(request, quotationEntity, productMap);

        // 存入新的明細
        quotationProductService.createAll(newQuotationProducts);

        // 重新計算金額
        PriceSummary priceSummary = calTotalCostPrice(newQuotationProducts, productMap);

        quotationEntity.setProducts(newQuotationProducts);
        quotationEntity.setTotalCostPrice(priceSummary.totalCostPrice());
        quotationEntity.setTotalPrice(priceSummary.totalPrice());
        quotationEntity.setTotalNegotiatedPrice(priceSummary.totalNegotiatedPrice());

        // 9️⃣ 更新報價單
        quotationService.edit(quotationEntity);
    }

    private QuotationEntity init(QuotationEntity quotationEntity, QuotationEditRequest request) {
        CustomerEntity customer = customerService.findByUuid(request.customerUuid());
        quotationEntity.setCustomer(customer);
        quotationEntity.setRemark(request.remark());
        return quotationEntity;
    }

    private List<QuotationProductEntity> initProducts(
            QuotationEditRequest request,
            QuotationEntity quotation,
            Map<String, ProductEntity> productMap) {
        return request.products().stream().map(p -> {
            ProductEntity productEntity = productMap.get(p.productUuid());
            QuotationProductEntity qpe = new QuotationProductEntity();
            qpe.setQuotation(quotation);
            qpe.setProduct(productEntity);
            qpe.setQuantity(p.quantity());
            qpe.setNegotiatedPrice(p.negotiatedPrice());
            return qpe;
        }).toList();
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
