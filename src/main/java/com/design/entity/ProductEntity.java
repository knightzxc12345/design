package com.design.entity;

import com.design.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

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

    // 編號
    @Column(
            name = "no",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String no;

    // 品名
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 規格
    @Column(
            name = "dimension",
            nullable = true,
            length = 128
    )
    private String dimension;

    // 描述
    @Column(
            name = "description",
            length = 512
    )
    private String description;

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

    // 成本金額
    @Column(
            name = "cost_price",
            nullable = false,
            precision = 12
    )
    @NotNull
    private BigDecimal costPrice;

    // 報價金額
    @Column(
            name = "price",
            nullable = false,
            precision = 12
    )
    @NotNull
    private BigDecimal price;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Where(clause = "is_deleted = false")
    private List<ProductItemEntity> items = new ArrayList<>();

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

    // 刪除人員
    @Column(
            name = "deleted_user",
            length = 36
    )
    private String deletedUser;

}
