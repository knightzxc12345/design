package com.erp.controller.item;

import com.erp.aop.annotation.Permission;
import com.erp.base.response.CustomResponse;
import com.erp.base.response.PageResponse;
import com.erp.base.response.enums.SystemCode;
import com.erp.controller.item.request.ItemCreateRequest;
import com.erp.controller.item.request.ItemEditRequest;
import com.erp.controller.item.request.ItemFindRequest;
import com.erp.controller.item.request.ItemPageRequest;
import com.erp.controller.item.response.ItemFindAllResponse;
import com.erp.controller.item.response.ItemFindResponse;
import com.erp.usecase.item.ItemCreateUseCase;
import com.erp.usecase.item.ItemDeleteUseCase;
import com.erp.usecase.item.ItemEditUseCase;
import com.erp.usecase.item.ItemFindUseCase;
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

@RequestMapping("/item")
@Tag(name = "品項")
@RestController
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemCreateUseCase itemCreateUseCase;

    private final ItemEditUseCase itemEditUseCase;

    private final ItemDeleteUseCase itemDeleteUseCase;

    private final ItemFindUseCase itemFindUseCase;

    @Permission("ITEM:CREATE")
    @Operation(summary = "建立")
    @PostMapping(
            value = "v1"
    )
    public CustomResponse create(
            @RequestBody @Validated @NotNull ItemCreateRequest request) {
        itemCreateUseCase.create(request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ITEM:EDIT")
    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse edit(
            @PathVariable("uuid") @NotNull UUID uuid,
            @RequestBody @Validated @NotNull ItemEditRequest request) {
        itemEditUseCase.edit(uuid, request);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ITEM:DELETE")
    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull UUID uuid) {
        itemDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Permission("ITEM:READ")
    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ItemFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull UUID uuid) {
        ItemFindResponse response = itemFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Permission("ITEM:READ")
    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1/all/{productUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @PathVariable("uuid") @NotNull UUID brandUuid,
            @Validated ItemFindRequest request) {
        List<ItemFindAllResponse> responses = itemFindUseCase.findAll(brandUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Permission("ITEM:READ")
    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page/{productUuid}"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = PageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @PathVariable("uuid") @NotNull UUID brandUuid,
            @Validated ItemPageRequest request) {
        PageResponse<ItemFindAllResponse> response = itemFindUseCase.findPage(brandUuid, request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
