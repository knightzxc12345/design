package com.erp.entity;

import com.erp.entity.enums.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

// 產品
@Table(name = "product", indexes = {
        @Index(name = "product_find", columnList = "uuid, is_deleted"),
        @Index(name = "product_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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

    // 說明
    @Column(
            name = "description",
            columnDefinition = "TEXT"
    )
    private String description;

    // 種類id
    @Column(
            name = "category_uuid",
            length = 36
    )
    private UUID categoryUuid;

    // 品牌id
    @Column(
            name = "brand_uuid",
            length = 36
    )
    private UUID brandUuid;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
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
