package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GreetingService;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final GreetingService greetingService;
    
    // 建構子注入（推薦方式）
    public HelloController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
	
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }
    
    @GetMapping("/sayhello")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return greetingService.greet(name);
    }
    
    @GetMapping("/welcome")
    public String welcome() {
        return greetingService.getWelcomeMessage();
    }
}
