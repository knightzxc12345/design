package com.design.controller.supplier.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record SupplierPageResponse(

        PageResponse page,

        List<SupplierFindAllResponse> responses

) {
}
