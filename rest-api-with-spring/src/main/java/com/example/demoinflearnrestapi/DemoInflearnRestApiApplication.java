package com.example.demoinflearnrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class DemoInflearnRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoInflearnRestApiApplication.class, args);
    }

}
