package com.design.entity;

import com.design.entity.enums.ItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

// 品項
@ToString(callSuper = true)
@Data
@Table(name = "item", indexes = {
        @Index(name = "item_find", columnList = "uuid, is_deleted"),
        @Index(name = "item_find_all", columnList = "pk, is_deleted")
})
@Entity
public class ItemEntity extends BaseEntity {

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

    // 尺寸
    @Column(
            name = "dimension",
            nullable = true,
            length = 64
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

    // 報價金額
    @Column(
            name = "price",
            nullable = false,
            precision = 12
    )
    @NotNull
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "supplier_uuid",
            referencedColumnName = "uuid",
            nullable = false
    )
    @NotNull
    private SupplierEntity supplier;

    // 狀態
    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private ItemStatus status;

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
