package com.design.controller.file.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FileFindAllResponse(

        @Schema(description = "報價單唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String quotationUuid,

        @Schema(description = "報價單編號", example = "DE20251030101010")
        String quotationNo,

        @Schema(description = "客戶名稱", example = "test")
        String customerName,

        @Schema(description = "檔案")
        List<FileFindAllResponse.File> files

) {

        public record File(

                @Schema(description = "標籤", example = "test")
                String tag,

                @Schema(description = "備註", example = "test")
                String remark,

                @Schema(description = "圖片 URL", example = "https://example.com/image.png")
                String imageUrl

        ){
        }

}
