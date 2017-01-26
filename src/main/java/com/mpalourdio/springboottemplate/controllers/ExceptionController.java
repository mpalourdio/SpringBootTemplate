package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.exception.CustomException;
import com.mpalourdio.springboottemplate.exception.ResponseObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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

    @GetMapping(value = "/call")
    public ResponseEntity callException() throws CustomException, IOException {
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<ResponseObject> exchange;

        final HttpHeaders httpHeaders = new HttpHeaders();
        final HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        try {
            exchange = restTemplate.exchange(
                    "http://localhost:8080/exception",
                    HttpMethod.GET,
                    httpEntity,
                    ResponseObject.class
            );
        } catch (final HttpClientErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }

        return exchange;
    }
}
