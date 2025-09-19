package com.design.entity;

import com.design.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// 產品
@ToString(callSuper = true)
@Data
@Table(name = "product", indexes = {
        @Index(name = "product_find", columnList = "uuid, is_deleted"),
        @Index(name = "product_find_all", columnList = "pk, is_deleted")
})
@Entity
public class ProductEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 代號
    @Column(
            name = "code",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String code;

    // 描述
    @Column(
            name = "description",
            length = 512
    )
    private String description;

    // 尺寸
    @Column(
            name = "dimension",
            nullable = true,
            length = 64
    )
    private String dimension;

    // 圖片
    @Column(
            name = "image_url"
    )
    private String imageUrl;

    // 單位
    @Column(
            name = "unit",
            nullable = false,
            length = 10
    )
    @NotBlank
    private String unit;

    // 售價
    @Column(
            name = "price",
            nullable = false,
            precision = 12
    )
    @NotNull
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_item",
            joinColumns = @JoinColumn(name = "product_uuid"),
            inverseJoinColumns = @JoinColumn(name = "item_uuid")
    )
    private List<ItemEntity> items = new ArrayList<>();

    // 狀態
    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus status;

    // 是否刪除
    @Column(
            name = "is_deleted",
            nullable = false
    )
    @NotNull
    private Boolean isDeleted;

    // 刪除時間
    @Column(
            name = "deleted_time"
    )
    private Instant deletedTime;

}
