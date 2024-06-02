package com.nhnacademy.springsecurityhomework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/admin")
    public String adminPage(){
        return "admin";
    }

    @GetMapping("/member")
    public String memberPage(){
        return "member";
    }

    @GetMapping("/google")
    public String googlePage(){
        return "google";
    }

    // 구글 계정으로 로그인해서 userdetailservice 커스텀해서 auth 값 새롭게 등록해버리기!

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

}
