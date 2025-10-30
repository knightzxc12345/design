package com.design.usecase.quotation.impl;

import com.design.base.api.CompanyType;
import com.design.base.common.Common;
import com.design.controller.quotation.response.QuotationDownloadResponse;
import com.design.entity.CustomerEntity;
import com.design.entity.ProductEntity;
import com.design.entity.QuotationEntity;
import com.design.entity.QuotationProductEntity;
import com.design.model.Quotation;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationDownloadUseCase;
import com.design.utils.ExcelUtil;
import com.design.utils.InstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationDownloadUseCaseImpl implements QuotationDownloadUseCase {

    @Value("classpath:reports/quotation_cathay.xlsx")
    private Resource quotationCathayResource;

    private final QuotationService quotationService;

    @Override
    public QuotationDownloadResponse download(CompanyType companyType, String uuid) {
        try{
            String date = InstantUtil.to(Instant.now(), Common.DATE_FORMAT_2);
            QuotationEntity quotationEntity = quotationService.findByUuid(uuid);
            Quotation quotation = convert(quotationEntity, date);
            byte[] file = ExcelUtil.convert(getInputStream(companyType), quotation);
            MediaType mediaType = MediaType.parseMediaType(Common.EXCEL_CONTENT_TYPE);
            return new QuotationDownloadResponse(
                    file,
                    mediaType
            );
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public QuotationDownloadResponse preview(CompanyType companyType, String uuid) {
        return null;
    }

    private InputStream getInputStream(CompanyType companyType){
        try {
            if(CompanyType.CATHAY.equals(companyType)){
                return quotationCathayResource.getInputStream();
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private Quotation convert(QuotationEntity quotationEntity, String date){
        CustomerEntity customerEntity = quotationEntity.getCustomer();
        List<QuotationProductEntity> quotationProductEntities = quotationEntity.getProducts();

        Quotation quotation = new Quotation();
        quotation.setCustomerName(customerEntity.getName());
        quotation.setCustomerPhone(String.format("%s%s", "分行電話：", customerEntity.getPhone()));
        quotation.setCustomerAddress(customerEntity.getAddress());
        quotation.setCustomerContactName(customerEntity.getContactName());
        quotation.setReportDate(String.format("%s%s", "報價單日期：", date));

        List<Quotation.Detail> details = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        int i = 0;
        for(QuotationProductEntity quotationProductEntity : quotationProductEntities){
            Quotation.Detail detail = new Quotation.Detail();
            ProductEntity productEntity = quotationProductEntity.getProduct();

            detail.setIndex(i++);
            detail.setProductNo(productEntity.getNo());
            detail.setProductName(productEntity.getName());
            detail.setProductDimension(productEntity.getDimension());
            detail.setProductUnit(productEntity.getUnit());
            detail.setProductQuantity(quotationProductEntity.getQuantity());
            detail.setProductCost(Common.NUMBER_FORMAT.format(productEntity.getCostPrice()));
            detail.setProductNegotiated(Common.NUMBER_FORMAT.format(quotationProductEntity.getNegotiatedPrice()));

            BigDecimal lineTotal = quotationProductEntity.getNegotiatedPrice()
                    .multiply(new BigDecimal(quotationProductEntity.getQuantity()));
            total = total.add(lineTotal);

            details.add(detail);
        }

        total = total.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal tax = total.multiply(new BigDecimal("0.05")).setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal totalWithTax = total.add(tax);

        quotation.setDetails(details);
        quotation.setTotal(Common.NUMBER_FORMAT.format(total));
        quotation.setTax(Common.NUMBER_FORMAT.format(tax));
        quotation.setTotalWithTax(Common.NUMBER_FORMAT.format(totalWithTax));

        return quotation;
    }


}
