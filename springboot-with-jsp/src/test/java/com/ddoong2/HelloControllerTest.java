package com.ddoong2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 루트_페이지를_호출하면_hello_뷰를_연결한다() throws Exception {
        mvc.perform(
                get("/")
        ).andExpect(view().name("hello"))
                .andExpect(model().attribute("message", "world"))
                .andExpect(status().isOk());
    }

    @Test
    public void hi_페이지를_호출하면_hi_뷰를_연결한다() throws Exception {
        mvc.perform(
                get("/hi")
        ).andExpect(view().name("hi"))
                .andExpect(model().attribute("message", "hi"))
                .andExpect(status().isOk());
    }
}