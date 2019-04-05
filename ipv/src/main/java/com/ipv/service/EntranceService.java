package com.ipv.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EntranceService {

    @GetMapping("/")
    public String index() {
        return "index";
    }

}
