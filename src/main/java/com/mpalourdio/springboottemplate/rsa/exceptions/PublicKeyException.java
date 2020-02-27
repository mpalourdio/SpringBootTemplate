package com.mpalourdio.springboottemplate.rsa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PublicKeyException extends RuntimeException {

    public PublicKeyException(String message) {
        super(message);
    }
}
