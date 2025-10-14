package com.design.usecase.quotation;

import com.design.controller.quotation.request.QuotationEditRequest;

public interface QuotationEditUseCase {

    void edit(String uuid, QuotationEditRequest request);

}
