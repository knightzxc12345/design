package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

// 產品品項
@ToString(callSuper = true)
@Data
@Table(name = "product_item", indexes = {
        @Index(name = "product_item_find", columnList = "uuid, is_deleted"),
        @Index(name = "product_item_find_all", columnList = "pk, is_deleted")
})
@Entity
public class ProductItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_uuid",
            nullable = false
    )
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "item_uuid",
            nullable = false
    )
    private ItemEntity item;

    @Column(
            name = "quantity",
            nullable = false
    )
    @NotNull
    private Integer quantity;

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
