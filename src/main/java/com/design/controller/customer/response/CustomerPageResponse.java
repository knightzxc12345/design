package com.design.controller.customer.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record CustomerPageResponse(

        PageResponse page,

        List<CustomerFindAllResponse> responses

) {
}
