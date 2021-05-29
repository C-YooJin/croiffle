package com.cfww.croiffle.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class WebController {

    @GetMapping("/")
    public String main() {
        return "/main";
    }


}
