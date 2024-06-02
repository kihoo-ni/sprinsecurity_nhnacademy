package com.nhnacademy.springsecurityhomework;

import com.nhnacademy.springsecurityhomework.config.RedisProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RedisProperties.class})
public class SpringsecurityhomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringsecurityhomeworkApplication.class, args);
    }

}
