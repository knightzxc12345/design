package com.erp.controller.permission;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/permission")
@Tag(name = "權限")
@RestController
@RequiredArgsConstructor
@Validated
public class PermissionController {



}
