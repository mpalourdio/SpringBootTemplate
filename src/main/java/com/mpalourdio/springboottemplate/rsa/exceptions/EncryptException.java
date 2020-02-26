package com.mpalourdio.springboottemplate.rsa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EncryptException extends RuntimeException {

    public EncryptException(String toEncrypt) {
        super(String.format("Unable to encrypt '%s'", toEncrypt));
    }
}
