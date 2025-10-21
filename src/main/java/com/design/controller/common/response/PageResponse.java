package com.design.controller.common.response;

public record PageResponse(

        int page,

        int size,

        long total,

        int totalPage

) {
}
