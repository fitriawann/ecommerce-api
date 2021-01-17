package com.gtech.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EcResourceNotFoundException extends RuntimeException {

    public EcResourceNotFoundException(String message) {
        super(message);
    }
}
