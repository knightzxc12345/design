package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationFindRequest;
import com.design.controller.quotation.request.QuotationPageRequest;
import com.design.controller.quotation.response.QuotationFindAllResponse;
import com.design.controller.quotation.response.QuotationFindResponse;
import com.design.controller.quotation.response.QuotationPageResponse;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationFindUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationFindUseCaseImpl implements QuotationFindUseCase {

    private final QuotationService quotationService;

    @Override
    public QuotationFindResponse findDetail(String uuid) {
        return null;
    }

    @Override
    public List<QuotationFindAllResponse> findAll(QuotationFindRequest request) {
        return null;
    }

    @Override
    public QuotationPageResponse findByPage(QuotationPageRequest request) {
        return null;
    }

}
