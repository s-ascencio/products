package com.falabella.product.infrastructure.controller;

import com.falabella.product.application.ApplicationException;
import com.falabella.product.application.dto.ErrorResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("ERROR-01");
        BindingResult result = ex.getBindingResult();
        error.setMessage(result.getFieldErrors().get(0).getDefaultMessage());
        error.setUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> psqlExceptionExceptionHandler(HttpServletRequest request, PSQLException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("ERROR-02");
        error.setMessage(ex.getMessage());
        error.setUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> applicationExceptionHandler(HttpServletRequest request, ApplicationException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setCode("ERROR-03");
        error.setMessage(ex.getMessage());
        error.setUrl(request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
