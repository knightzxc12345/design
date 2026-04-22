package com.erp.usecase.material.impl;

import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialDeleteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialDeleteUseCaseImpl implements MaterialDeleteUseCase {

    private final MaterialService materialService;

}
