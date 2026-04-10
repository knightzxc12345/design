package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

// 供應商聯絡人
@Table(name = "supplier_contract", indexes = {
        @Index(name = "supplier_contract_find", columnList = "uuid, is_deleted"),
        @Index(name = "supplier_contract_find_all", columnList = "pk, is_deleted")
})
@Entity
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SupplierContractEntity extends BaseEntity {

    // 供應商id
    @Column(
            name = "supplier_uuid",
            nullable = false,
            length = 36
    )
    @NotNull
    private UUID supplierUuid;

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String name;

    // 電話
    @Column(
            name = "phone",
            length = 32
    )
    private String phone;

    // 信箱
    @Column(
            name = "email",
            length = 128
    )
    private String email;

    // 職稱
    @Column(
            name = "title",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String title;

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
