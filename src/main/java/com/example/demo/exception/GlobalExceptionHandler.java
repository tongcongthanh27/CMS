package com.example.demo.exception;


import java.util.Map;
import java.util.Objects;

import com.example.demo.dto.request.ApiResponse;
import jakarta.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import lombok.extern.slf4j.Slf4j;   // bat loi o service
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
    @Slf4j
    public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Object>> handlingRuntimeException(Exception exception) {
            log.error("Exception: ", exception);
            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
            apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }

        @ExceptionHandler(AppException.class)
        public ResponseEntity<ApiResponse<Object>> handlingAppException(AppException exception) {
            ErrorCode errorCode = exception.getErrorCode();
            ApiResponse<Object> apiResponse = new ApiResponse<>();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
            return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
        }
    }
