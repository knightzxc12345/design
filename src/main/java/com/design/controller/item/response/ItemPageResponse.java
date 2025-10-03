package com.design.controller.item.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record ItemPageResponse(

        PageResponse page,

        List<ItemFindAllResponse> responses

) {
}
