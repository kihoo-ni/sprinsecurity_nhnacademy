package com.nhnacademy.springsecurityhomework.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.springsecurityhomework.domain.Member;
import com.nhnacademy.springsecurityhomework.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
@JsonTest
class JacksonConfigTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerialization() throws JsonProcessingException {



        Member member = new Member("1234","이기훈", "1234", 29, Role.MEMBER);

        String json = objectMapper.writeValueAsString(member);


        assertEquals("{\"id\":\"1234\",\"name\":\"이기훈\",\"password\":\"1234\",\"age\":\"29\",\"role\":\"member\"}", json);
    }
}