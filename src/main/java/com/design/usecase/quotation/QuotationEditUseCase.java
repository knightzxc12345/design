package com.design.usecase.quotation;

import com.design.controller.quotation.request.QuotationEditRequest;
import com.design.entity.enums.QuotationStatus;

public interface QuotationEditUseCase {

    void edit(String uuid, QuotationEditRequest request);

    void updateStatus(String uuid, QuotationStatus status);

}
