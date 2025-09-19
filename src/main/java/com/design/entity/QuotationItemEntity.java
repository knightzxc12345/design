package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

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

}
