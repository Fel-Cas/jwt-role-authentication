package com.jwt.authentication.exceptions;

import com.jwt.authentication.dto.ErrorResponse;
import com.jwt.authentication.exceptions.errors.BadRequestException;
import com.jwt.authentication.exceptions.errors.ForbiddenException;
import com.jwt.authentication.exceptions.errors.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> badRequestException(Exception exception, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,exception.getMessage(), LocalDateTime.now() ,
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> notFoundException(Exception exception, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse(HttpStatus.NOT_FOUND,exception.getMessage(), LocalDateTime.now() ,
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ErrorResponse> forbiddenException(Exception exception, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse(HttpStatus.FORBIDDEN,exception.getMessage(), LocalDateTime.now() ,
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }



}
