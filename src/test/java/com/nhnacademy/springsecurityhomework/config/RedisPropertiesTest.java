package com.nhnacademy.springsecurityhomework.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest
 class RedisPropertiesTest {

    @Autowired
    private RedisProperties redisProperties;



    @Test
     void testRedisPropertiesBinding() {
        assertThat(redisProperties).isNotNull();
        assertThat(redisProperties.getHost()).isEqualTo("133.186.241.167");
        assertThat(redisProperties.getPort()).isEqualTo(6379);
        assertThat(redisProperties.getPassword()).isEqualTo("*N2vya7H@muDTwdNMR!");
        assertThat(redisProperties.getDatabase()).isEqualTo(30);
    }
    }