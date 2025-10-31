package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

// 檔案
@ToString(callSuper = true)
@Data
@Table(name = "file", indexes = {
        @Index(name = "file_find", columnList = "uuid"),
        @Index(name = "file_find_all", columnList = "pk")
})
@Entity
public class FileEntity extends BaseEntity {

    // 對應報價單
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_uuid", nullable = false)
    private QuotationEntity quotation;

    // 標籤
    @Column(
            name = "tag",
            nullable = false,
            unique = false,
            length = 64
    )
    @NotBlank
    private String tag;

    // 備註
    @Column(
            name = "remark",
            columnDefinition = "TEXT"
    )
    private String remark;

    // 圖片
    @Column(
            name = "image_url"
    )
    @NotBlank
    private String imageUrl;

}
