package com.design.usecase.quotation.impl;

import com.design.controller.quotation.request.QuotationEditRequest;
import com.design.service.QuotationService;
import com.design.usecase.quotation.QuotationEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotationEditUseCaseImpl implements QuotationEditUseCase {

    private final QuotationService quotationService;

    @Override
    public void edit(String uuid, QuotationEditRequest request) {

    }

}
