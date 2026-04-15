package com.erp.service;

import com.erp.entity.BrandEntity;
import com.erp.entity.enums.BrandStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BrandService {

    BrandEntity create(BrandEntity brandEntity);

    BrandEntity edit(BrandEntity brandEntity);

    BrandEntity delete(UUID uuid);

    BrandEntity findByUuid(UUID uuid);

    List<BrandEntity> findAll(String keyword, BrandStatus brandStatus);

    Page<BrandEntity> findByPage(Pageable pageable, String keyword, BrandStatus brandStatus);

}
