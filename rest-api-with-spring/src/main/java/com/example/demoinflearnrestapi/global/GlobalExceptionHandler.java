package com.example.demoinflearnrestapi.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BaseException.class)
    public String handleBaseException(BaseException e){
        log.info("BaseException Advice");
        return e.getMessage();
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        log.info("Exception Advice");
        return ResponseEntity.badRequest().build();
    }
}
