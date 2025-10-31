package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 標籤
    @Column(
            name = "tag",
            length = 64
    )
    private String tag;

    // 備註
    @Column(
            name = "remark",
            columnDefinition = "TEXT"
    )
    private String remark;

    // 父節點
    @Column(
            name = "parent_uuid",
            length = 36
    )
    private String parentUuid;

    // 是否為資料夾
    @Column(
            name = "is_folder",
            nullable = false
    )
    private Boolean isFolder = false;

    // 多張圖片
    @OneToMany(
            mappedBy = "file",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FileDetailEntity> fileDetails = new ArrayList<>();

}
