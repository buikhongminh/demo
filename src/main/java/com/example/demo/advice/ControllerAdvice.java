package com.example.demo.advice;

import com.example.demo.advice.exception.ResourceNotFoundException;
import com.example.demo.entity.common.CommonResponse;
import com.example.demo.entity.common.StatusCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {
    public static final String INVALID_BODY_ERROR_DELIMITER = ",";
    public static final String INVALID_BODY_ERROR_MESSAGE = "Invalid values for fields: %s";
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGeneralException(Exception ex, WebRequest request,
                                                                 HttpServletResponse response) {
        log.info(ex.getMessage());
        var body = CommonResponse.builder()
                .code(StatusCode.INTERNAL_ERROR)
                .message("Internal Server Error")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse> handleNotFoundException(ResourceNotFoundException ex,
                                                                  HttpServletResponse response) {
        log.info(ex.getMessage());
        addErrorResponseHeader(response);
        var body = CommonResponse.builder()
                .code(StatusCode.NOT_FOUND)
                .message("Not Found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleBadRequestException(MethodArgumentNotValidException ex,
                                                                    WebRequest request,
                                                                    HttpServletResponse response) {
        log.info(ex.getMessage());
        addErrorResponseHeader(response);
        var listOfErrorFields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField).collect(Collectors.joining(INVALID_BODY_ERROR_DELIMITER));
        var message = String.format(INVALID_BODY_ERROR_MESSAGE, listOfErrorFields);
        var body = CommonResponse.builder()
                .code(StatusCode.INVALID_PARAMETER)
                .message(message)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    private void addErrorResponseHeader(HttpServletResponse response) {
        response.addHeader(HttpHeaders.CONTENT_TYPE, "application/problem+json");
    }
}
