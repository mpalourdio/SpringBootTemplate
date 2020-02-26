package com.mpalourdio.springboottemplate.rsa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PrivateKeyException extends RuntimeException {

    public PrivateKeyException() {
        super("Unable to get the private Key");
    }
}
