package com.design.controller.item;

import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import com.design.controller.item.request.ItemCreateRequest;
import com.design.controller.item.request.ItemEditRequest;
import com.design.controller.item.request.ItemFindRequest;
import com.design.controller.item.request.ItemPageRequest;
import com.design.controller.item.response.ItemFindAllResponse;
import com.design.controller.item.response.ItemFindResponse;
import com.design.controller.item.response.ItemPageResponse;
import com.design.usecase.item.ItemCreateUseCase;
import com.design.usecase.item.ItemDeleteUseCase;
import com.design.usecase.item.ItemEditUseCase;
import com.design.usecase.item.ItemFindUseCase;
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

    @Operation(summary = "建立")
    @PostMapping(
            value = "v1",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CustomResponse create(
            @ModelAttribute @Validated @NotNull ItemCreateRequest request,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        itemCreateUseCase.create(request, file);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "編輯")
    @PutMapping(
            value = "v1/{uuid}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CustomResponse update(
            @PathVariable("uuid") @NotNull String uuid,
            @ModelAttribute @Validated @NotNull ItemEditRequest request,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        itemEditUseCase.edit(uuid, request, file);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "刪除")
    @DeleteMapping(
            value = "v1/{uuid}"
    )
    public CustomResponse delete(
            @PathVariable("uuid") @NotNull String uuid) {
        itemDeleteUseCase.delete(uuid);
        return new CustomResponse(SystemCode.SUCCESS);
    }

    @Operation(summary = "透過Id取得")
    @GetMapping(
            value = "v1/{uuid}"
    )
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = ItemFindResponse.class))
    })
    public CustomResponse findByUuid(
            @PathVariable("uuid") @NotNull String uuid) {
        ItemFindResponse response = itemFindUseCase.findDetail(uuid);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

    @Operation(summary = "取得清單")
    @GetMapping(
            value = "v1"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemFindAllResponse.class))),
            }),
    })
    public CustomResponse findAll(
            @Validated ItemFindRequest request) {
        List<ItemFindAllResponse> responses = itemFindUseCase.findAll(request);
        return new CustomResponse(SystemCode.SUCCESS, responses);
    }

    @Operation(summary = "取得分頁")
    @GetMapping(
            value = "v1/page"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200 - 清單", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ItemPageResponse.class))),
            }),
    })
    public CustomResponse findPage(
            @Validated ItemPageRequest request) {
        ItemPageResponse response = itemFindUseCase.findByPage(request);
        return new CustomResponse(SystemCode.SUCCESS, response);
    }

}
