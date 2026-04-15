package com.erp.entity;

import com.erp.entity.enums.ItemStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

// 品項
@Table(name = "item", indexes = {
        @Index(name = "item_find", columnList = "uuid, is_deleted"),
        @Index(name = "item_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ItemEntity extends BaseEntity {

    // 產品id
    @Column(
            name = "product_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID productUuid;

    // sku code
    @Column(
            name = "sku_code",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String skuCode;

    // 規格
    @Column(
            name = "spec",
            length = 128
    )
    private String spec;

    // 價格
    @Column(
            name = "price",
            nullable = false
    )
    @NotBlank
    private BigDecimal price;

    // 成本
    @Column(
            name = "cost",
            nullable = false
    )
    @NotBlank
    private BigDecimal cost;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
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
    private Boolean isDeleted = false;

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
    private UUID deletedUser;

}
