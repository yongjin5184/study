package com.example.testcodewithspring.controller;

import com.example.testcodewithspring.TestCodeWithSpringApplication;
import com.example.testcodewithspring.domain.dto.ProductDto;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestCodeWithSpringApplication.class, webEnvironment = RANDOM_PORT)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private Gson gson;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    public void 조회_정상케이스() throws Exception{
        this.mockMvc.perform(get("/contents/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    public void 저장_정상케이스() throws Exception {
        this.mockMvc.perform(post("/contents/product")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .content(getSaveProductJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }

    @Test
    public void 저장후_조회_정상케이스() throws Exception{
        저장_정상케이스();
        조회_정상케이스();
    }

    public String getSaveProductJson(){
        ProductDto productDto = ProductDto.builder().id(1L).productName("상품").build();
        return gson.toJson(productDto);
    }
}

