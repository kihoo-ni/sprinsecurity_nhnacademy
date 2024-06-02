package com.nhnacademy.springsecurityhomework.service;


import com.nhnacademy.springsecurityhomework.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RedisTemplate<String, Member> memberRedisTemplate;


    private String HASH_NAME = "Member:";


    //if Member exist
    public boolean existMember(String MemberId) {
        return memberRedisTemplate.opsForHash().hasKey(HASH_NAME, MemberId);
    }


    //Member save
    public Member saveMember(Member member) {

        member.setId(member.getId());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRedisTemplate.opsForHash().put(HASH_NAME, member.getId(), member);
        return member;

    }

    //멤버 단건을 조회하는 API만들기
    public Member getMember(String id) {

        return (Member) memberRedisTemplate.opsForHash().get(HASH_NAME, id);
    }
}
