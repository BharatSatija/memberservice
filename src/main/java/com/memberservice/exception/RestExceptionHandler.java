package com.memberservice.exception;


import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.DateTimeException;


@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if(NestedExceptionUtils.getRootCause(ex) instanceof DateTimeException){
            ErrorResponse errorResponse = new ErrorResponse("Invalid date format");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }
}

