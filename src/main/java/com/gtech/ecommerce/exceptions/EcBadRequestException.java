package com.gtech.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EcBadRequestException extends RuntimeException {

    public EcBadRequestException(String message) {
        super(message);
    }
}
