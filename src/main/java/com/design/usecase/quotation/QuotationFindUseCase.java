package com.design.usecase.quotation;

import com.design.controller.quotation.request.QuotationFindRequest;
import com.design.controller.quotation.request.QuotationPageRequest;
import com.design.controller.quotation.response.QuotationFindAllResponse;
import com.design.controller.quotation.response.QuotationFindResponse;
import com.design.controller.quotation.response.QuotationPageResponse;

import java.util.List;

public interface QuotationFindUseCase {

    QuotationFindResponse findDetail(String uuid);

    List<QuotationFindAllResponse> findAll(QuotationFindRequest request);

    QuotationPageResponse findByPage(QuotationPageRequest request);

}
