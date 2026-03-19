package com.erp.service.impl;

import com.erp.repository.ActionRepository;
import com.erp.service.ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

}
