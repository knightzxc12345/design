package com.design.controller.supplier.requst;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SupplierPageRequest(

        SupplierFindRequest filter,

        @Min(value = 0, message = "頁數不得小於0")
        @NotNull(message = "頁數不得為空")
        int page,

        @Min(value = 10, message = "頁數不得小於10")
        @NotNull(message = "頁數不得為空")
        int size

) {
}
