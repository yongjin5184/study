package com.example.demoinflearnrestapi.controller;

import com.example.demoinflearnrestapi.global.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BaseExceptionController {

    @GetMapping("/api/v1/base-exception")
    public String throwBaseException() {
        log.info("throwBaseException");
        throw new BaseException();
    }

    @GetMapping("/api/v1/runtime-exception")
    public String throwRuntimeException() {
        log.info("throwRuntimeException");
        throw new RuntimeException();
    }

    @GetMapping("/api/v1/performance-logger")
    public String performanceLoggerByAspectj() {
        log.info("performanceLoggerByAspectj");
        return "SUCCESS";
    }
}
