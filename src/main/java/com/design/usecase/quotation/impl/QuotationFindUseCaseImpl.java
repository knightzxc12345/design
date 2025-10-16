package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationFindRequest;
import com.design.controller.quotation.request.QuotationPageRequest;
import com.design.controller.quotation.response.QuotationFindAllResponse;
import com.design.controller.quotation.response.QuotationFindResponse;
import com.design.controller.quotation.response.QuotationPageResponse;
import com.design.entity.CustomerEntity;
import com.design.entity.ProductEntity;
import com.design.entity.QuotationEntity;
import com.design.entity.QuotationProductEntity;
import com.design.service.ProductService;
import com.design.service.QuotationProductService;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationFindUseCaseImpl implements QuotationFindUseCase {

    private final QuotationService quotationService;

    private final QuotationProductService quotationProductService;

    private final ProductService productService;

    @Override
    public QuotationFindResponse findDetail(String uuid) {
        QuotationEntity quotationEntity = quotationService.findByUuid(uuid);
        return convertDetail(quotationEntity);
    }

    @Override
    public List<QuotationFindAllResponse> findAll(QuotationFindRequest request) {
        return null;
    }

    @Override
    public QuotationPageResponse findByPage(QuotationPageRequest request) {
        return null;
    }

    private QuotationFindResponse convertDetail(QuotationEntity quotationEntity){
        CustomerEntity customerEntity = quotationEntity.getCustomer();
        List<QuotationProductEntity> quotationProductEntities = quotationEntity.getProducts();
        List<QuotationFindResponse.Product> responses = new ArrayList<>();
        for(QuotationProductEntity quotationProductEntity : quotationProductEntities){
            ProductEntity productEntity = quotationProductEntity.getProduct();
            responses.add(new QuotationFindResponse.Product(
                    productEntity.getUuid(),
                    productEntity.getName(),
                    quotationProductEntity.getQuantity(),
                    productEntity.getCostPrice(),
                    productEntity.getPrice(),
                    quotationProductEntity.getNegotiatedPrice()
            ));
        }
        return new QuotationFindResponse(
                quotationEntity.getQuotationNo(),
                quotationEntity.getTotalCostPrice(),
                quotationEntity.getTotalPrice(),
                quotationEntity.getTotalNegotiatedPrice(),
                quotationEntity.getRemark(),
                new QuotationFindResponse.Customer(
                        customerEntity.getUuid(),
                        customerEntity.getName(),
                        customerEntity.getPhone(),
                        customerEntity.getFax(),
                        customerEntity.getEmail(),
                        customerEntity.getAddress(),
                        customerEntity.getVatNumber(),
                        customerEntity.getContactName(),
                        customerEntity.getContactPhone(),
                        customerEntity.getRemark()
                ),
                responses
        );
    }

}
