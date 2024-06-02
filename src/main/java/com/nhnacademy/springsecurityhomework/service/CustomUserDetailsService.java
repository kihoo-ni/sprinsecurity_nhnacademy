package com.nhnacademy.springsecurityhomework.service;

import com.nhnacademy.springsecurityhomework.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;
    public CustomUserDetailsService( MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member= memberService.getMember(username);
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
   return new User(member.getId(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_"+member.getRole().getAuthRole())));
    }


}

