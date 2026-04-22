package com.erp.controller.bom;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.bom.request.BomItemCreateRequest;
import com.erp.controller.bom.request.BomItemEditRequest;
import com.erp.controller.bom.response.BomItemFindAllResponse;
import com.erp.controller.bom.response.BomItemFindResponse;
import com.erp.usecase.boom.BomItemCreateUseCase;
import com.erp.usecase.boom.BomItemDeleteUseCase;
import com.erp.usecase.boom.BomItemEditUseCase;
import com.erp.usecase.boom.BomItemFindUseCase;
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

@RequestMapping("/bom_item")
@Tag(name = "BomItem")
@RestController
@RequiredArgsConstructor
@Validated
public class BomItemController {

    private final BomItemCreateUseCase bomItemCreateUseCase;

    private final BomItemEditUseCase bomItemEditUseCase;

    private final BomItemDeleteUseCase bomItemDeleteUseCase;

    private final BomItemFindUseCase bomItemFindUseCase;

    @Permission("BOM:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull BomItemCreateRequest request) {
        bomItemCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull BomItemEditRequest request) {
        bomItemEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        bomItemDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("BOM:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = BomItemFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        BomItemFindResponse response = bomItemFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("BOM:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all/{bomUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = BomItemFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(@PathVariable("bomUuid") @NotNull UUID bomUuid) {
        List<BomItemFindAllResponse> responses = bomItemFindUseCase.findAll(bomUuid);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

}
