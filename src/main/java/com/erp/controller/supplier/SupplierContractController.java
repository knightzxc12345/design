package com.erp.controller.supplier;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.supplier.request.SupplierContractCreateRequest;
import com.erp.controller.supplier.request.SupplierContractEditRequest;
import com.erp.controller.supplier.response.SupplierContractFindAllResponse;
import com.erp.controller.supplier.response.SupplierContractFindResponse;
import com.erp.usecase.supplier.SupplierContractCreateUseCase;
import com.erp.usecase.supplier.SupplierContractDeleteUseCase;
import com.erp.usecase.supplier.SupplierContractEditUseCase;
import com.erp.usecase.supplier.SupplierContractFindUseCase;
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

@RequestMapping("/supplier_contract")
@Tag(name = "供應商")
@RestController
@RequiredArgsConstructor
@Validated
public class SupplierContractController {

    private final SupplierContractCreateUseCase supplierContractCreateUseCase;

    private final SupplierContractEditUseCase supplierContractEditUseCase;

    private final SupplierContractDeleteUseCase supplierContractDeleteUseCase;

    private final SupplierContractFindUseCase supplierContractFindUseCase;

    @Permission("SUPPLIER:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull SupplierContractCreateRequest request) {
        supplierContractCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull SupplierContractEditRequest request) {
        supplierContractEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        supplierContractDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("SUPPLIER:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = SupplierContractFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        SupplierContractFindResponse response = supplierContractFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("SUPPLIER:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all/{supplierUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = SupplierContractFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(@PathVariable("supplierUuid") @NotNull UUID supplierUuid) {
        List<SupplierContractFindAllResponse> responses = supplierContractFindUseCase.findAll(supplierUuid);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

}
