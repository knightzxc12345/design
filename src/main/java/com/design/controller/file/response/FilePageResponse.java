package com.design.controller.file.response;

import com.design.controller.common.response.PageResponse;

import java.util.List;

public record FilePageResponse(

        PageResponse page,

        List<FileFindAllResponse> responses

) {
}
