package com.nhnacademy.springsecurityhomework.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ActiveProfiles("test")
class RedisConfigTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    void testRedisConnection() {
        String hkey="hashkey2";
        String key = "testKey1";
        String value = "testValue";

        // Redis에 값을 저장합니다.
        redisTemplate.opsForHash().put(hkey, key, value);

        // Redis에서 값을 읽어옵니다.
        String retrievedValue = (String) redisTemplate.opsForHash().get(hkey, key);

        // 값을 검증합니다.
        assertThat(retrievedValue).isEqualTo(value);
    }


}