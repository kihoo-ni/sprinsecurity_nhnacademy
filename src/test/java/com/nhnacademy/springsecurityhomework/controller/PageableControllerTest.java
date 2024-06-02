package com.nhnacademy.springsecurityhomework.controller;

import com.nhnacademy.springsecurityhomework.config.CustomPageableHandlerMethodArgumentResolver;
import com.nhnacademy.springsecurityhomework.exception.PageSizeOverDefaultSizeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PageableController.class)
public class PageableControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomPageableHandlerMethodArgumentResolver resolver;

    @Test
    public void testGetMember() throws Exception {
        String expectedContent = "Page request [number: 0, size 5, sort: UNSORTED]";

        mockMvc.perform(get("/pageable?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent))
                .andDo(print());
    }

    @Test
    public void testHandlePageSizeOverDefaultSizeException() throws Exception {
        // Perform the GET request with invalid page size
        mockMvc.perform(get("/pageable?page=0&size=15"))
                .andExpect(status().isOk())
                .andExpect(view().name("pageableError"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", instanceOf(PageSizeOverDefaultSizeException.class)));
    }
}
