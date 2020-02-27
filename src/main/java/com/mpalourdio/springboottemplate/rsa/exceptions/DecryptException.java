package com.mpalourdio.springboottemplate.rsa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DecryptException extends RuntimeException {

    public DecryptException(String message) {
        super(message);
    }
}
