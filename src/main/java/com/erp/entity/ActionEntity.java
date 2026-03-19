package com.erp.entity;

import com.erp.entity.enums.ActionMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
@Table(name = "action", indexes = {
        @Index(name = "action_find", columnList = "uuid"),
        @Index(name = "action_find_all", columnList = "pk")
})
@Entity
public class ActionEntity extends BaseEntity {

    // 名稱
    @Column(
            name = "name",
            nullable = false,
            length = 32
    )
    @NotBlank
    private String name;

    // 方法
    @Column(
            name = "method",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    @NotNull
    private ActionMethod method;

    // 排序
    @Column(
            name = "sort",
            nullable = false
    )
    @NotNull
    private Integer sort;

}