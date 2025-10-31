package com.design.controller.file.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record FileFindAllResponse(

        @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String uuid,

        @Schema(description = "報價單唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String quotationUuid,

        @Schema(description = "父節點唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
        String parentUuid,

        @Schema(description = "資料夾名稱", example = "test")
        String name,

        @Schema(description = "標籤", example = "test")
        String tag,

        @Schema(description = "備註", example = "test")
        String remark,

        @Schema(description = "圖片清單", example = "test")
        List<File> files


) {

        public record File(

                @Schema(description = "唯一值", example = "7d934fb6-e5b7-45db-a117-feaf75d19a9f")
                String uuid,

                @Schema(description = "客戶名稱", example = "test")
                String name,

                @Schema(description = "標籤", example = "test")
                String tag,

                @Schema(description = "備註", example = "test")
                String remark,

                @Schema(description = "圖片網址", example = "test")
                String imageUrl

        ){
        }

}
