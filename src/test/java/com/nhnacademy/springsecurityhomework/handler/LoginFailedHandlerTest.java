package com.nhnacademy.springsecurityhomework.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.AuthenticationException;

import static org.mockito.Mockito.*;

public class LoginFailedHandlerTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;


    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private LoginFailedHandler loginFailedHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void onAuthenticationFailure_ExceedingLoginAttempts_ShouldRedirectToLoginPage() throws Exception {
        // Implement test logic here
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);


        when(redisTemplate.opsForValue().get("login_failure_counter")).thenReturn(6);
        loginFailedHandler.onAuthenticationFailure(request, response, exception);
        verify(response).sendRedirect("/members/auth");
    }
}