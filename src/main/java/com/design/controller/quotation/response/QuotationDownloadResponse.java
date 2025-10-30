package com.design.controller.quotation.response;

import org.springframework.http.MediaType;

public record QuotationDownloadResponse(

        byte [] file,

        MediaType mediaType

) {
}
