package com.design.controller.product;

import com.design.usecase.product.ProductCreateUseCase;
import com.design.usecase.product.ProductDeleteUseCase;
import com.design.usecase.product.ProductEditUseCase;
import com.design.usecase.product.ProductFindUseCase;
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

    private final ProductCreateUseCase productCreateUseCase;

    private final ProductEditUseCase productEditUseCase;

    private final ProductDeleteUseCase productDeleteUseCase;

    private final ProductFindUseCase productFindUseCase;

}
