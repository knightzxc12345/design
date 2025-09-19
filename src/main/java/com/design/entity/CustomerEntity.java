package com.design.entity;

import com.design.base.regex.BaseRegex;
import com.design.entity.enums.CustomerStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;

// customer
@ToString(callSuper = true)
@Data
@Table(name = "customer", indexes = {
        @Index(name = "customer_find", columnList = "uuid, is_deleted"),
        @Index(name = "customer_find_all", columnList = "pk, is_deleted")
})
@Entity
public class CustomerEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 128
    )
    @NotBlank
    private String name;

    // 電話
    @Column(
            name = "phone",
            length = 32
    )
    private String phone;

    // 傳真
    @Column(
            name = "fax",
            length = 32
    )
    private String fax;

    // 信箱
    @Column(
            name = "email",
            length = 128
    )
    @Email
    private String email;

    // 地址
    @Column(
            name = "address"
    )
    private String address;

    // 統一編號
    @Column(
            name = "vat_number",
            length = 8
    )
    private String vatNumber;

    // 聯絡人
    @Column(
            name = "contact_name",
            nullable = false,
            length = 64
    )
    @NotBlank
    private String contactName;

    // 聯絡人電話
    @Column(
            name = "contact_phone",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String contactPhone;

    // 備註
    @Column(
            name = "remark",
            columnDefinition = "TEXT"
    )
    private String remark;

    // 狀態
    @Column(
            name = "status",
            nullable = false,
            length = 32
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private CustomerStatus status;

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
