package com.nhnacademy.springsecurityhomework.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LoginFailedHandler extends SimpleUrlAuthenticationFailureHandler {


    private static final String LOGIN_FAILURE_KEY = "login_failure_counter";

    private final RedisTemplate<String, Object> redisTemplate;

    public LoginFailedHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("Login failed");


        Integer counter = (Integer) redisTemplate.opsForValue().get(LOGIN_FAILURE_KEY);

        if (counter == null) {
            counter = 0;
        }

        counter++;

        redisTemplate.opsForValue().set(LOGIN_FAILURE_KEY, counter);


        if (counter > 5) {
            log.warn("로그인 실패횟수 :" + counter);


        }



        response.sendRedirect("/members/auth");
    }

}
