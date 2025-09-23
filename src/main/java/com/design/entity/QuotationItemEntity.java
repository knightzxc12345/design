package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

// 報價單明細
@ToString(callSuper = true)
@Data
@Table(name = "quotation_item", indexes = {
        @Index(name = "quotation_item_find", columnList = "uuid"),
        @Index(name = "quotation_item_find_all", columnList = "pk")
})
@Entity
public class QuotationItemEntity extends BaseEntity {

    // 所屬報價單
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "quotation_uuid",
            nullable = false
    )
    private QuotationEntity quotation;

    // 產品
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_uuid",
            nullable = false
    )
    private ProductEntity product;

    // 數量
    @Column(
            name = "quantity",
            nullable = false
    )
    @NotNull
    private Integer quantity;

    // 議價金額
    @Column(
            name = "negotiated_price",
            nullable = false,
            precision = 15
    )
    @NotNull
    private BigDecimal negotiatedPrice;

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
