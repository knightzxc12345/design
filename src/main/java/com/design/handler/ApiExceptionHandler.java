package com.design.handler;

import com.design.base.api.CodeMessage;
import com.design.base.api.CustomResponse;
import com.design.base.api.SystemCode;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final HttpServletRequest request;

    /**
     * HTTP METHOD錯誤
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00002",
            description = "不支援的HTTP METHOD"
    )
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * Header錯誤
     *
     * @param ex
     * @return
     * */
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00003",
            description = "缺少必要的Request Header"
    )
    public ResponseEntity<?> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 參數錯誤
     *
     * @param ex
     * @return
     * */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00004",
            description = "參數錯誤"
    )
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 參數錯誤
     *
     * @param ex
     * @return
     * */
    @ExceptionHandler(InvalidParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00004",
            description = "參數錯誤"
    )
    public ResponseEntity<?> handleInvalidParamRequest(InvalidParamException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 請球錯誤
     *
     * @param ex
     * @return
     * */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00004",
            description = "參數錯誤"
    )
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 系統錯誤
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00005",
            description = "系統錯誤"
    )
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 請球錯誤
     *
     * @param ex
     * @return
     * */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponse(
            responseCode = "A00012",
            description = "請求錯誤"
    )
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse(SystemCode.SYSTEM_ERROR)
                );
    }

    /**
     * 不明的錯誤
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        CodeMessage codeMessage = (CodeMessage) ex.getStatus();
        log.error("error Code : {}", codeMessage.getCode());
        log.error("error Message : {}", codeMessage.getMessage());
        log.error("error", ex);
        return ResponseEntity
                .badRequest()
                .body(
                        new CustomResponse<>((CodeMessage) ex.getStatus())
                );
    }

    /**
     * 對外API
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        log.error("error", ex);
        return ResponseEntity
                .unprocessableEntity()
                .body(
                        new CustomResponse<>(ex.getCode(), ex.getMessage())
                );
    }

}