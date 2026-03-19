package com.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@ToString(callSuper = true)
@Data
@Table(name = "permission", indexes = {
        @Index(name = "permission_find", columnList = "uuid"),
        @Index(name = "permission_find", columnList = "parent_uuid"),
        @Index(name = "permission_find_all", columnList = "pk")
})
@Entity
public class PermissionEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String name;

    // 網址
    @Column(
            name = "url",
            nullable = false,
            length = 128
    )
    @NotBlank
    private String url;

    // 父權線Uuid
    @Column(
            name = "parent_uuid",
            length = 36
    )
    private UUID parentUuid;

    // 排序
    @Column(
            name = "sort",
            nullable = false
    )
    @NotNull
    private Integer sort;

}