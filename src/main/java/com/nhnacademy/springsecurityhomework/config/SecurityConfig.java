package com.nhnacademy.springsecurityhomework.config;


import com.nhnacademy.springsecurityhomework.handler.LoginFailedHandler;
import com.nhnacademy.springsecurityhomework.handler.LoginSuccessHandler;
import com.nhnacademy.springsecurityhomework.service.CustomUserDetailsService;
import com.nhnacademy.springsecurityhomework.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    final
    LoginSuccessHandler loginSuccessHandler;
    final
    LoginFailedHandler loginFailedHandler;

    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, LoginFailedHandler loginFailedHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailedHandler = loginFailedHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.oauth2Login(oauth2Login -> oauth2Login.successHandler(loginSuccessHandler).failureHandler(loginFailedHandler));
        http.formLogin(login -> login.loginPage("/members/auth").usernameParameter("id").passwordParameter("password").loginProcessingUrl("/login").successHandler(loginSuccessHandler).failureHandler(loginFailedHandler));
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/pageable","/members/auth", "/members", "/members/**").permitAll()
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/member").hasAuthority("ROLE_MEMBER")
                        .requestMatchers("/google").hasAuthority("ROLE_GOOGLE")
                        .anyRequest().authenticated()
        );


        return http.build();
    }




    @Bean
    public CustomUserDetailsService customUserDetailsService(MemberService memberService) {
        return new CustomUserDetailsService(memberService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
