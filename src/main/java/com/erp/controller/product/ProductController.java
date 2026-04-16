package com.erp.controller.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product")
@Tag(name = "產品")
@RestController
@RequiredArgsConstructor
@Validated
public class ProductController {



}
