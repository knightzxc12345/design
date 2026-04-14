package com.erp.controller.category;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.category.request.CategoryCreateRequest;
import com.erp.controller.category.request.CategoryEditRequest;
import com.erp.controller.category.request.CategoryFindRequest;
import com.erp.controller.category.request.CategoryPageRequest;
import com.erp.controller.category.response.CategoryFindAllResponse;
import com.erp.controller.category.response.CategoryFindResponse;
import com.erp.usecase.category.CategoryCreateUseCase;
import com.erp.usecase.category.CategoryDeleteUseCase;
import com.erp.usecase.category.CategoryEditUseCase;
import com.erp.usecase.category.CategoryFindUseCase;
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

@RequestMapping("/category")
@Tag(name = "種類")
@RestController
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryCreateUseCase categoryCreateUseCase;

    private final CategoryEditUseCase categoryEditUseCase;

    private final CategoryDeleteUseCase categoryDeleteUseCase;

    private final CategoryFindUseCase categoryFindUseCase;

    @Permission("CATEGORY:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull CategoryCreateRequest request) {
        categoryCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CATEGORY:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull CategoryEditRequest request) {
        categoryEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CATEGORY:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        categoryDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("CATEGORY:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = CategoryFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        CategoryFindResponse response = categoryFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("CATEGORY:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated CategoryFindRequest request) {
        List<CategoryFindAllResponse> responses = categoryFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("CATEGORY:READ")
    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated CategoryPageRequest request) {
        PageResponse<CategoryFindAllResponse> response = categoryFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
