package com.nhnacademy.springsecurityhomework.service;

import com.nhnacademy.springsecurityhomework.domain.Member;
import com.nhnacademy.springsecurityhomework.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RedisTemplate<String, Member> redisTemplate;

    @Mock
    private HashOperations<String, String, Member> hashOperations;

    private Member member;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and member object
        MockitoAnnotations.initMocks(this);
        member = new Member("1", "1", "password", 29, Role.MEMBER);
        when(redisTemplate.<String, Member>opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void testExistMember() {
        String memberId = member.getId();
        when(hashOperations.hasKey("Member:", memberId)).thenReturn(true);

        boolean exists = memberService.existMember(memberId);

        assertTrue(exists);
        verify(hashOperations, times(1)).hasKey("Member:", memberId);
    }

    @Test
    public void testSaveMember() {
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(member.getPassword())).thenReturn(encodedPassword);

        Member savedMember = memberService.saveMember(member);

        assertNotNull(savedMember);
        assertEquals(member.getId(), savedMember.getId());
        assertEquals(encodedPassword, savedMember.getPassword());
        verify(hashOperations, times(1)).put("Member:", member.getId(), member);
    }

    @Test
    public void testGetMember() {
        String memberId = member.getId();
        when(hashOperations.get("Member:", memberId)).thenReturn(member);

        Member foundMember = memberService.getMember(memberId);

        assertNotNull(foundMember);
        assertEquals(memberId, foundMember.getId());
        assertEquals(member, foundMember);
        verify(hashOperations, times(1)).get("Member:", memberId);
    }
}
