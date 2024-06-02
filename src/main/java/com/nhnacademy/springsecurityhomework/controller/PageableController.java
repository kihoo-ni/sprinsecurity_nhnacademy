package com.nhnacademy.springsecurityhomework.controller;

import com.nhnacademy.springsecurityhomework.exception.PageSizeOverDefaultSizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class PageableController {



    @ExceptionHandler({PageSizeOverDefaultSizeException.class})
    public String handleLoginException(Model model, PageSizeOverDefaultSizeException e){
        model.addAttribute("error", e);
        return "pageableError";
    }


    @GetMapping("/pageable")
    @ResponseBody
    public String getMember(Pageable pageable){

        log.warn(pageable.toString());

        return pageable.toString();
    }

}
