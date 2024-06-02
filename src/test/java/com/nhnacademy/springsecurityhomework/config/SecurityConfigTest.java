package com.nhnacademy.springsecurityhomework.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"MEMBER"})
    public void accessAdminPage_WithUserRole_ShouldReturnIsForbidden() throws Exception {
        // Implement test logic here
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }



    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void accessAdminPage_WithUserRole_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
    public void accessAdminPage_WithAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    public void accessMemberPage_WithUserRole_ShouldReturnIsForbidden() throws Exception {
        // Implement test logic here
        mockMvc.perform(MockMvcRequestBuilders.get("/member"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }



    @Test
    @WithMockUser(username = "user", roles = {"MEMBER"})
    public void accessMemberPage_WithUserRole_ShouldReturnIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_MEMBER"})
    public void accessMemberPage_WithAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/member"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(username = "user", roles = {"MEMBER"})
    public void accessGooglePage_WithUserRole_ShouldReturnIsForbidden() throws Exception {
        // Implement test logic here
        mockMvc.perform(MockMvcRequestBuilders.get("/google"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }



    @Test
    @WithMockUser(username = "user", roles = {"GOOGLE"})
    public void accessGooglePage_WithUserRole_ShouldReturnIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/google"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_GOOGLE"})
    public void accessGooglePage_WithAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/google"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @WithMockUser(username = "user", authorities = {"ROLE_GOOGLE"})
    public void accessMainPage_WithAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    @WithMockUser(username = "user")
    public void accessMainPage_WithNoAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    public void accessLoginPage_WithNoAuthorities_ShouldReturnisOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members/auth"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}