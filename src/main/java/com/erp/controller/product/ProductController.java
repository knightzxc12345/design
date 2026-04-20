package com.erp.controller.product;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.product.request.ProductCreateRequest;
import com.erp.controller.product.request.ProductEditRequest;
import com.erp.controller.product.request.ProductFindRequest;
import com.erp.controller.product.request.ProductPageRequest;
import com.erp.controller.product.response.ProductFindAllResponse;
import com.erp.controller.product.response.ProductFindResponse;
import com.erp.usecase.product.ProductCreateUseCase;
import com.erp.usecase.product.ProductDeleteUseCase;
import com.erp.usecase.product.ProductEditUseCase;
import com.erp.usecase.product.ProductFindUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @Permission("PRODUCT:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull ProductCreateRequest request) {
        productCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PRODUCT:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull ProductEditRequest request) {
        productEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PRODUCT:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        productDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("PRODUCT:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ProductFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        ProductFindResponse response = productFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("PRODUCT:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all/{brandUuid}/{categoryUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ProductFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @PathVariable("brandUuid") @NotNull UUID brandUuid,
            @PathVariable("categoryUuid") @NotNull UUID categoryUuid,
            @Validated ProductFindRequest request) {
        List<ProductFindAllResponse> responses = productFindUseCase.findAll(brandUuid, categoryUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("PRODUCT:READ")
    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page/{brandUuid}/{categoryUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @PathVariable("brandUuid") @NotNull UUID brandUuid,
            @PathVariable("categoryUuid") @NotNull UUID categoryUuid,
            @Validated ProductPageRequest request) {
        PageResponse<ProductFindAllResponse> response = productFindUseCase.findPage(brandUuid, categoryUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
