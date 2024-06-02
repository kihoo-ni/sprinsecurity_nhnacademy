package com.nhnacademy.springsecurityhomework.controller;

import com.nhnacademy.springsecurityhomework.domain.Member;
import com.nhnacademy.springsecurityhomework.domain.Role;
import com.nhnacademy.springsecurityhomework.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MemberController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void createMember_Success() throws Exception {
        Member member = new Member("1", "이기훈", "10", 15, Role.MEMBER);
        Mockito.when(memberService.saveMember(Mockito.any(Member.class))).thenReturn(member);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"이기훈\", \"password\": \"10\", \"age\": 15, \"role\": \"MEMBER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("이기훈"))
                .andExpect(jsonPath("$.password").value("10"))
                .andExpect(jsonPath("$.age").value(15))
                .andExpect(jsonPath("$.role").value("member"));
    }

    @Test
    void createMember_AlreadyExist() throws Exception {
        Mockito.when(memberService.existMember(anyString())).thenReturn(true);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"이기훈\", \"password\": \"10\", \"age\": 15, \"role\": \"MEMBER\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("이미 존재하는 멤버입니다."));
    }

    @Test
    void getMember_Success() throws Exception {
        Member member = new Member("1", "이기훈", "10", 15, Role.MEMBER);
        Mockito.when(memberService.getMember(anyString())).thenReturn(member);

        mockMvc.perform(get("/members/{memberId}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("이기훈"))
                .andExpect(jsonPath("$.password").value("10"))
                .andExpect(jsonPath("$.age").value(15))
                .andExpect(jsonPath("$.role").value("member"));
    }
}
