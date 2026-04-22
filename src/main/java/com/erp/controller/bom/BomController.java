package com.erp.controller.bom;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.bom.request.BomCreateRequest;
import com.erp.controller.bom.request.BomEditRequest;
import com.erp.controller.bom.request.BomFindRequest;
import com.erp.controller.bom.request.BomPageRequest;
import com.erp.controller.bom.response.BomFindAllResponse;
import com.erp.controller.bom.response.BomFindResponse;
import com.erp.usecase.boom.BomCreateUseCase;
import com.erp.usecase.boom.BomDeleteUseCase;
import com.erp.usecase.boom.BomEditUseCase;
import com.erp.usecase.boom.BomFindUseCase;
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

@RequestMapping("/bom")
@Tag(name = "Bom")
@RestController
@RequiredArgsConstructor
@Validated
public class BomController {

    private final BomCreateUseCase bomCreateUseCase;

    private final BomEditUseCase bomEditUseCase;

    private final BomDeleteUseCase bomDeleteUseCase;

    private final BomFindUseCase bomFindUseCase;

    @Permission("BOM:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull BomCreateRequest request) {
        bomCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull BomEditRequest request) {
        bomEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        bomDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = BomFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        BomFindResponse response = bomFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("BOM:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all/{itemUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BomFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @PathVariable("uuid") @NotNull UUID itemUuid,
            @Validated BomFindRequest request) {
        List<BomFindAllResponse> responses = bomFindUseCase.findAll(itemUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("BOM:READ")
    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page/{itemUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @PathVariable("uuid") @NotNull UUID itemUuid,
            @Validated BomPageRequest request) {
        PageResponse<BomFindAllResponse> response = bomFindUseCase.findPage(itemUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
