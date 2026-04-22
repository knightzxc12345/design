package com.erp.controller.material;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/material")
@Tag(name = "材料")
@RestController
@RequiredArgsConstructor
@Validated
public class MaterialController {



}
