package com.mpalourdio.springboottemplate.rsa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class KeyStoreLoadException extends RuntimeException {

    public KeyStoreLoadException(String message) {
        super(message);
    }
}
