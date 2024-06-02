package com.nhnacademy.springsecurityhomework.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private static final String LOGIN_FAILURE_KEY="login_failure_counter";

    private final RedisTemplate<String, Object> redisTemplate;

    public LoginSuccessHandler(RedisTemplate<String, Object> redisTemplate) {
        log.info("login Success constructor");
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("login Success");

       redisTemplate.opsForValue().set(LOGIN_FAILURE_KEY, 0);

        response.sendRedirect("/main");
    }

}
