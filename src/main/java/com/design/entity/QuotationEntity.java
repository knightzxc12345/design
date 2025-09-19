package com.design.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// 報價單
@ToString(callSuper = true)
@Data
@Table(name = "quotation", indexes = {
        @Index(name = "quotation_find", columnList = "uuid"),
        @Index(name = "quotation_find_all", columnList = "pk")
})
@Entity
public class QuotationEntity extends BaseEntity {

    // 單號
    @Column(
            name = "quotation_no",
            nullable = false,
            unique = true,
            length = 64
    )
    @NotBlank
    private String quotationNo;

    // 對應客戶
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_uuid", nullable = false)
    private CustomerEntity customer;

    // 報價單明細
    @OneToMany(
            mappedBy = "quotation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<QuotationItemEntity> items = new ArrayList<>();

}
