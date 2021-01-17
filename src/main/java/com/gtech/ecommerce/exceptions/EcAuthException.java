package com.gtech.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class EcAuthException extends RuntimeException {

    public EcAuthException(String message) {
        super(message);
    }
}
