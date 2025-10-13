package com.design.controller.product;

import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import com.design.controller.product.request.ProductCreateRequest;
import com.design.controller.product.request.ProductEditRequest;
import com.design.controller.product.request.ProductFindRequest;
import com.design.controller.product.request.ProductPageRequest;
import com.design.controller.product.response.ProductFindAllResponse;
import com.design.controller.product.response.ProductFindResponse;
import com.design.controller.product.response.ProductPageResponse;
import com.design.usecase.product.ProductCreateUseCase;
import com.design.usecase.product.ProductDeleteUseCase;
import com.design.usecase.product.ProductEditUseCase;
import com.design.usecase.product.ProductFindUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CustomResponse create(
            @ModelAttribute @Validated @NotNull ProductCreateRequest request,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        productCreateUseCase.create(request, file);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CustomResponse update(
            @PathVariable("uuid") @NotNull String uuid,
            @ModelAttribute @Validated @NotNull ProductEditRequest request,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        productEditUseCase.edit(uuid, request, file);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull String uuid) {
        productDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ProductFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull String uuid) {
        ProductFindResponse response = productFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ProductFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated ProductFindRequest request) {
        List<ProductFindAllResponse> responses = productFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ProductPageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated ProductPageRequest request) {
        ProductPageResponse response = productFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
