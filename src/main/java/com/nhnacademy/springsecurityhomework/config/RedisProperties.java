package com.nhnacademy.springsecurityhomework.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("spring.data.redis")
public class RedisProperties {

    private final String host;
    private final int port;
    private final String password;
    private final int database;

    public RedisProperties(String host, int port, String password, int database) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.database = database;
    }
}
