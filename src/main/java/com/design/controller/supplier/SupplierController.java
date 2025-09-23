package com.design.controller.supplier;

import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import com.design.controller.supplier.requst.SupplierCreateRequest;
import com.design.controller.supplier.requst.SupplierEditRequest;
import com.design.controller.supplier.requst.SupplierFindRequest;
import com.design.controller.supplier.requst.SupplierPageRequest;
import com.design.controller.supplier.response.SupplierFindAllResponse;
import com.design.controller.supplier.response.SupplierFindResponse;
import com.design.controller.supplier.response.SupplierPageResponse;
import com.design.usecase.supplier.SupplierCreateUseCase;
import com.design.usecase.supplier.SupplierDeleteUseCase;
import com.design.usecase.supplier.SupplierEditUseCase;
import com.design.usecase.supplier.SupplierFindUseCase;
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

@RequestMapping("/supplier")
@Tag(name = "廠商")
@RestController
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final SupplierCreateUseCase supplierCreateUseCase;

    private final SupplierEditUseCase supplierEditUseCase;

    private final SupplierDeleteUseCase supplierDeleteUseCase;

    private final SupplierFindUseCase supplierFindUseCase;

    @Operation(summary = "建立廠商")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull SupplierCreateRequest request) {
        supplierCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "編輯廠商")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse update(
            @PathVariable("uuid") @NotNull String uuid,
            @RequestBody @Validated @NotNull SupplierEditRequest request) {
        supplierEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "刪除廠商")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull String uuid) {
        supplierDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "透過Id取得廠商")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SupplierFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull String uuid) {
        SupplierFindResponse response = supplierFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "取得廠商清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = SupplierFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated SupplierFindRequest request) {
        List<SupplierFindAllResponse> responses = supplierFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Operation(summary = "取得廠商分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = SupplierPageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated SupplierPageRequest request) {
        SupplierPageResponse response = supplierFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
