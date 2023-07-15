package com.demo.urlshortener;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        //This could be replaced with a logger
        System.err.println(ex.getMessage());

        return new ResponseEntity<>("Error occurred while processing your request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}