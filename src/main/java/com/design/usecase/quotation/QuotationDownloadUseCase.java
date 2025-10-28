package com.design.usecase.quotation;

import com.design.base.api.CompanyType;
import com.design.controller.quotation.response.QuotationDownloadResponse;

public interface QuotationDownloadUseCase {

    QuotationDownloadResponse download(CompanyType companyType, String uuid);

}
