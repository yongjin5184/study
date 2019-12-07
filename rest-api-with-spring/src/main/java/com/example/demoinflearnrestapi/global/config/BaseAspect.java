package com.example.demoinflearnrestapi.global.config;

import com.google.common.base.Joiner;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class BaseAspect {
    Logger logger =  LoggerFactory.getLogger(BaseAspect.class);

    @Around("execution(* com.example.demoinflearnrestapi.controller.*.*(..))")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String params = getRequestParams(request);

        long start = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs()); // 6
        } finally {
            long end = System.currentTimeMillis();

            logger.info("Request: {} {}{} < {} ({}ms)", request.getMethod(), request.getRequestURI(),
                params, request.getRemoteHost(), end - start);
        }
    }

    private String getRequestParams(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();

        String params = "";
        if (!paramMap.isEmpty()) {
            params = " {" + paramMapToString(paramMap) + "}";
        }
        return params;
    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
            .map(entry -> String.format("%s: %s",
                entry.getKey(), Joiner.on(",").join(entry.getValue())))
            .collect(Collectors.joining(", "));
    }
}
