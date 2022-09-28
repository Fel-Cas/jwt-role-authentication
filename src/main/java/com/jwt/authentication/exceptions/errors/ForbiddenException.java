package com.jwt.authentication.exceptions.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ForbiddenException extends ResponseStatusException {
    public ForbiddenException(String message){
        super(HttpStatus.FORBIDDEN,message);
    }
}
