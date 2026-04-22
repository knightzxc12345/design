package com.erp.service;

import com.erp.entity.MaterialEntity;
import com.erp.entity.enums.MaterialStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface MaterialService {

    MaterialEntity create(MaterialEntity materialEntity);

    MaterialEntity edit(MaterialEntity materialEntity);

    MaterialEntity delete(UUID uuid);

    MaterialEntity findByUuid(UUID uuid);

    List<MaterialEntity> findAll(
            UUID supplierUuid,
            String keyword,
            MaterialStatus materialStatus
    );

    Page<MaterialEntity> findPage(
            Pageable pageable,
            UUID supplierUuid,
            String keyword,
            MaterialStatus materialStatus
    );

}
