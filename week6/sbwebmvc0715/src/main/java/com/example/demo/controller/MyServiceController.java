package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GreetingService;

@RestController
@RequestMapping("/srv")
public class MyServiceController {
	
	@Autowired
    GreetingService greetingService;
	
	@GetMapping("/greet")
    public String hello(@RequestParam(defaultValue = "Service") String name) {
        return greetingService.greet(name);
    }
}
