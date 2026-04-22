package com.erp.usecase.material.impl;

import com.erp.service.MaterialService;
import com.erp.usecase.material.MaterialEditUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialEditUseCaseImpl implements MaterialEditUseCase {

    private final MaterialService materialService;

}
