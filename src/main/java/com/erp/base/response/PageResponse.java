package com.erp.base.response;

import java.util.List;

public record PageResponse<T>(

        int page,

        int size,

        long total,

        int totalPage,

        List<T> responses

) {
}
