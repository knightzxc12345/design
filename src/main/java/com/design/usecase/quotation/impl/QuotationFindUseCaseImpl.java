package com.design.usecase.quotation.impl;

import com.design.controller.common.response.PageResponse;
import com.design.controller.quotation.request.QuotationFindRequest;
import com.design.controller.quotation.request.QuotationPageRequest;
import com.design.controller.quotation.response.QuotationFindAllResponse;
import com.design.controller.quotation.response.QuotationFindResponse;
import com.design.controller.quotation.response.QuotationPageResponse;
import com.design.entity.*;
import com.design.service.QuotationService;
import com.design.service.UserService;
import com.design.usecase.quotation.QuotationFindUseCase;
import com.design.utils.InstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationFindUseCaseImpl implements QuotationFindUseCase {

    private final QuotationService quotationService;

    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public QuotationFindResponse findDetail(String uuid) {
        QuotationEntity quotationEntity = quotationService.findByUuid(uuid);
        return convertDetail(quotationEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuotationFindAllResponse> findAll(QuotationFindRequest request) {
        List<QuotationEntity> quotationEntities = quotationService.findAll(
                request.keyword(),
                InstantUtil.to(request.startTime()),
                InstantUtil.to(request.endTime()),
                request.quotationStatus()
        );
        return convertList(quotationEntities);
    }

    @Transactional(readOnly = true)
    @Override
    public QuotationPageResponse findByPage(QuotationPageRequest request) {
        Page<QuotationEntity> quotationEntityPage = quotationService.findByPage(
                request.keyword(),
                InstantUtil.to(request.startTime()),
                InstantUtil.to(request.endTime()),
                request.quotationStatus(),
                PageRequest.of(request.page(), request.size())
        );
        return convertPage(quotationEntityPage);
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
                    productEntity.getCode(),
                    productEntity.getDimension(),
                    productEntity.getUnit(),
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

    private List<QuotationFindAllResponse> convertList(List<QuotationEntity> quotationEntities){
        if(null == quotationEntities || quotationEntities.isEmpty()){
            return List.of();
        }
        List<UserEntity> userEntities = userService.findAll();
        Map<String, UserEntity> userEntityMap = userEntities.stream()
                .collect(Collectors.toMap(UserEntity::getUuid, Function.identity()));
        List<QuotationFindAllResponse> responses = new ArrayList<>();
        for(QuotationEntity quotationEntity : quotationEntities){
            responses.add(new QuotationFindAllResponse(
                    quotationEntity.getUuid(),
                    quotationEntity.getQuotationNo(),
                    quotationEntity.getCustomer().getName(),
                    quotationEntity.getTotalCostPrice(),
                    quotationEntity.getTotalPrice(),
                    quotationEntity.getTotalNegotiatedPrice(),
                    quotationEntity.getStatus(),
                    InstantUtil.to(quotationEntity.getCreateTime()),
                    userEntityMap.get(quotationEntity.getCreateUser()).getUsername()
            ));
        }
        return responses;
    }

    private QuotationPageResponse convertPage(Page<QuotationEntity> quotationEntityPage){
        List<QuotationFindAllResponse> responses = convertList(quotationEntityPage.getContent());
        return new QuotationPageResponse(
                new PageResponse(
                        quotationEntityPage.getNumber(),
                        quotationEntityPage.getSize(),
                        quotationEntityPage.getTotalElements(),
                        quotationEntityPage.getTotalPages()
                ),
                responses
        );
    }

}
