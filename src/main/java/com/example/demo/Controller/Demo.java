package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
public class Demo {

    @GetMapping("/demo")
    public String demo() {
        System.out.println("Connected!");
        return "Hello World";
    }
}
