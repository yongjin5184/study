package com.example.demoinflearnrestapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BaseExceptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void BaseException_Controller_Advice_테스트() throws Exception {
        mockMvc.perform(get("/api/v1/base-exception"))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void RuntimeException_Controller_Advice_테스트() throws Exception {
        mockMvc.perform(get("/api/v1/runtime-exception"))
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }
}
