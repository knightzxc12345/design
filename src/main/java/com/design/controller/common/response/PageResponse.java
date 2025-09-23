package com.design.controller.common.response;

public record PageResponse(

        int page,

        int size,

        int total

) {
}
