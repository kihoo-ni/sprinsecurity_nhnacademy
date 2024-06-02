package com.nhnacademy.springsecurityhomework.controller;


import com.nhnacademy.springsecurityhomework.domain.Member;
import com.nhnacademy.springsecurityhomework.exception.AlreadyExistMemberException;
import com.nhnacademy.springsecurityhomework.exception.NotFoundRoleException;
import com.nhnacademy.springsecurityhomework.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Validated
public class MemberController {

    MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @ExceptionHandler(NotFoundRoleException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundRoleException(NotFoundRoleException ex, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", "Bad Request");
        responseBody.put("message", ex.getMessage());
        responseBody.put("path", request.getRequestURI());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(AlreadyExistMemberException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExistMemberException(AlreadyExistMemberException ex, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", HttpStatus.CONFLICT.value());
        responseBody.put("error", "Conflict");
        responseBody.put("message", ex.getMessage());
        responseBody.put("path", request.getRequestURI());

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }



    @PostMapping("/members")
    public Member createMember(@Valid @RequestBody Member member) {
        if (memberService.existMember(member.getId())) {
            throw new AlreadyExistMemberException("이미 존재하는 멤버입니다.");
        } else {

            return memberService.saveMember(member);
        }

    }


    @GetMapping("/members/{memberId}")
    public Member getMember(@PathVariable Long memberId) {
        return memberService.getMember(String.valueOf(memberId));
    }



}
