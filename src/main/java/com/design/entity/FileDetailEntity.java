package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

// 檔案明細
@ToString(callSuper = true)
@Data
@Table(name = "file_detail", indexes = {
        @Index(name = "file_detail_find", columnList = "uuid"),
        @Index(name = "file_detail_find_all", columnList = "pk")
})
@Entity
public class FileDetailEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "file_uuid",
            nullable = false
    )
    private FileEntity file;

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 圖片 URL
    @Column(
            name = "image_url",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String imageUrl;

}
