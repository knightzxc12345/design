package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationCreateRequest;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotationCreateUseCaseImpl implements QuotationCreateUseCase {

    private final QuotationService quotationService;

    @Override
    public void create(QuotationCreateRequest request) {

    }

}
