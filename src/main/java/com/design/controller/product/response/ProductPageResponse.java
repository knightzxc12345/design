package com.design.controller.product.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record ProductPageResponse(

        PageResponse page,

        List<ProductFindAllResponse> responses

) {
}
