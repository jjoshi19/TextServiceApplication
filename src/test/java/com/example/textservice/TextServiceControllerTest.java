package com.example.textservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TextServiceController.class)
public class TextServiceControllerTest {

    @Autowired
    MockMvc mvc;
    @Test
    public void testGetCamelize() throws Exception{
        RequestBuilder request = get("/camelize")
                .param("original", "this_is_a_thing")
                .param("initialCap", "true");
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("ThisIsAThing"));


    }
    @Test
    public void testGetCamelizeInitialCapFalse() throws Exception{
        RequestBuilder request = get("/camelize")
                .param("original", "this_is_a_thing")
                .param("initialCap", "false");
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("thisIsAThing"));


    }

    @Test
    public void testGetRedact() throws  Exception{
        RequestBuilder request = get("/redact")
                .param("original", "A little of this and a little of that")
                .param("badWord", "little")
                .param("badWord", "this");
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("A ****** of **** and a ****** of that"));
    }
    @Test
    public void testGetRedactOneBadWord() throws  Exception{
        RequestBuilder request = get("/redact")
                .param("original", "A little of this and a little of that")
                .param("badWord", "little");

        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("A ****** of this and a ****** of that"));
    }
    @Test
    public void testFindAndReplace() throws  Exception{
        String find="little", replace="lot";
        RequestBuilder request = post("/s/{find}/{replacement}", find, replace )
                .contentType(MediaType.ALL)
                .content("a little of this and a little of that");


        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("a lot of this and a lot of that"));
    }
    @Test
    public void testEncode() throws  Exception{
        String message = "a little of this and a little of that";
        String key="mcxrstunopqabyzdefghijklvw";

        RequestBuilder request = post("/encode").param("message",message)
                .param("key",key);
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string("m aohhas zt hnog myr m aohhas zt hnmh"));
    }
}
