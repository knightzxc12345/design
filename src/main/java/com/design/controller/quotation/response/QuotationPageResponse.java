package com.design.controller.quotation.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record QuotationPageResponse(

        PageResponse page,

        List<QuotationFindAllResponse> responses

) {
}
