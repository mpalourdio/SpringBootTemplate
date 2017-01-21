package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.exception.CustomException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String throwException() throws CustomException {
        if (1 == 1) {
            throw new CustomException("bad");
        }

        return "all is ok";
    }
}
