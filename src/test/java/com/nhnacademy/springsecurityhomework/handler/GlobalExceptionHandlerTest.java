
package com.nhnacademy.springsecurityhomework.handler;

import com.nhnacademy.springsecurityhomework.controller.MemberController;
import com.nhnacademy.springsecurityhomework.exception.AlreadyExistMemberException;
import com.nhnacademy.springsecurityhomework.exception.NotFoundRoleException;
import com.nhnacademy.springsecurityhomework.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void handleAlreadyExistMemberException() throws Exception {
        Mockito.when(memberService.saveMember(Mockito.any())).thenThrow(new AlreadyExistMemberException("이미 존재하는 멤버입니다."));

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\", \"name\": \"이기훈\", \"password\": \"10\", \"age\": 15, \"role\": \"member\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("이미 존재하는 멤버입니다."));
    }

    @Test
    void handleNotFoundRoleException() throws Exception {
        Mockito.when(memberService.saveMember(Mockito.any())).thenThrow(new NotFoundRoleException("invalidRole은 없는 롤 입니다."));

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"2\", \"name\": \"이기훈\", \"password\": \"10\", \"age\": 15, \"role\": \"invalidRole\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("invalidrole은 없는 롤 입니다."));
    }
}
