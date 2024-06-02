package com.nhnacademy.springsecurityhomework.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nhnacademy.springsecurityhomework.domain.Member;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;


@Component
public class MemberRedisSerializer implements RedisSerializer<Member> {
    private final ObjectMapper objectMapper;

    public MemberRedisSerializer() {
        this.objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Integer.class, new IntegerSerializer());
        objectMapper.registerModule(module);
    }

    @Override
    public byte[] serialize(Member member) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(member);
        } catch (Exception e) {
            throw new SerializationException("Error serializing Member", e);
        }
    }

    @Override
    public Member deserialize(byte[] bytes) throws SerializationException {
        try {
            return objectMapper.readValue(bytes, Member.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing Member", e);
        }
    }
}
