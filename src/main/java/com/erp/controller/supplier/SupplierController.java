package com.erp.controller.supplier;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.supplier.request.SupplierCreateRequest;
import com.erp.controller.supplier.request.SupplierEditRequest;
import com.erp.controller.supplier.request.SupplierFindRequest;
import com.erp.controller.supplier.request.SupplierPageRequest;
import com.erp.controller.supplier.response.SupplierFindAllResponse;
import com.erp.controller.supplier.response.SupplierFindResponse;
import com.erp.usecase.supplier.SupplierCreateUseCase;
import com.erp.usecase.supplier.SupplierDeleteUseCase;
import com.erp.usecase.supplier.SupplierEditUseCase;
import com.erp.usecase.supplier.SupplierFindUseCase;
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

@RequestMapping("/supplier")
@Tag(name = "供應商")
@RestController
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final SupplierCreateUseCase supplierCreateUseCase;

    private final SupplierEditUseCase supplierEditUseCase;

    private final SupplierDeleteUseCase supplierDeleteUseCase;

    private final SupplierFindUseCase supplierFindUseCase;

    @Permission("SUPPLIER:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull SupplierCreateRequest request) {
        supplierCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull SupplierEditRequest request) {
        supplierEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        supplierDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SupplierFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        SupplierFindResponse response = supplierFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("SUPPLIER:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all"
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

    @Permission("SUPPLIER:READ")
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
            @Validated SupplierPageRequest request) {
        PageResponse response = supplierFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
