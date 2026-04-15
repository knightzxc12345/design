package com.erp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class ItemEntity extends BrandEntity {

    

}
