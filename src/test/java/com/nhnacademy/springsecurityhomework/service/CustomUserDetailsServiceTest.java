package com.nhnacademy.springsecurityhomework.service;

import com.nhnacademy.springsecurityhomework.domain.Member;
import com.nhnacademy.springsecurityhomework.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername_ExistingUser_ReturnsUserDetails() {
        String username = "testuser";
        Member member = new Member();
        member.setId(username);
        member.setPassword("$2a$10$5yk0ltixJKsdhTBfh.kKUOeZb6VbXHmrIocUaRdRd77PylOsJ7o4u");
        member.setRole(Role.MEMBER);

        when(memberService.getMember(username)).thenReturn(member);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(member.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_MEMBER", userDetails.getAuthorities().iterator().next().getAuthority());
        verify(memberService, times(1)).getMember(username);
    }

    @Test
    public void loadUserByUsername_NonExistingUser_ThrowsUsernameNotFoundException() {
        // Arrange
        String username = "nonexistinguser";
        when(memberService.getMember(username)).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
        verify(memberService, times(1)).getMember(username);
    }
}
