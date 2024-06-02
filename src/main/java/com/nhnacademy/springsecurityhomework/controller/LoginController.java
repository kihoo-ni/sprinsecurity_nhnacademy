package com.nhnacademy.springsecurityhomework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/members/auth")
    public String loginPage(){
        return "login";
    }

}
