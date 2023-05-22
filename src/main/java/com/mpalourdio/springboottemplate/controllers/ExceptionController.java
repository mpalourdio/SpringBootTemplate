package com.mpalourdio.springboottemplate.controllers;

import com.mpalourdio.springboottemplate.exception.CustomException;
import com.mpalourdio.springboottemplate.exception.ResponseObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping(value = "nok", produces = MediaType.APPLICATION_JSON_VALUE)
    public String throwException() throws CustomException {
        if (1 == 1) {
            throw new CustomException("bad");
        }

        return "all is ok if here";
    }

    @GetMapping(value = "ok", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject ok() {

        var responseObject = new ResponseObject();
        responseObject.property = "toto";

        return responseObject;
    }


    private ResponseEntity<?> callException(String param) {
        var restTemplate = new RestTemplate();
        ResponseEntity<ResponseObject> exchange;

        var httpHeaders = new HttpHeaders();
        var httpEntity = new HttpEntity<>(httpHeaders);
        try {
            exchange = restTemplate.exchange(
                    "http://localhost:8080/exception/" + param,
                    HttpMethod.GET,
                    httpEntity,
                    ResponseObject.class
            );
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }

        return exchange;
    }

    @GetMapping(value = "/callok")
    public ResponseEntity<?> getCallOk() {
        var call = callException("ok");

        if (call.getStatusCode() != HttpStatus.OK) {
            var responseObject = (ResponseObject) call.getBody();
            //do things here
        }

        return call;
    }

    @GetMapping(value = "/callnok")
    public ResponseEntity<?> getCallNok() {
        return callException("nok");
    }
}
