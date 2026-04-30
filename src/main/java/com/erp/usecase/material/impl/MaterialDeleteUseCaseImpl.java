package com.erp.usecase.material.impl;

import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaterialDeleteUseCaseImpl implements MaterialDeleteUseCase {

    private final MaterialService materialService;

    @Override
    public void delete(UUID uuid) {
        materialService.delete(uuid);
    }

}
