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
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.*;

public class LoginSuccessHandlerTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private LoginSuccessHandler loginSuccessHandler;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and member object
        MockitoAnnotations.initMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void onAuthenticationSuccess_ValidUser_ShouldResetLoginFailureCounterAndRedirectToMain() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        // Act
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // Assert
        verify(valueOperations).set("login_failure_counter", 0);
        verify(response).sendRedirect("/main");
    }
}
