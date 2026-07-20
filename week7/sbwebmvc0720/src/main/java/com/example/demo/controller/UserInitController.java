package com.example.demo.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user-init")
public class UserInitController implements CommandLineRunner {

	private final UserService userService;

	public UserInitController(UserService userService) {
		this.userService = userService;
	}
   
	@Override
	public void run(String... args) throws Exception {
		// 初始化使用者資料
		userService.createUser("Alice", "alice@example.com",20);
		userService.createUser("Bob", "bob@example.com",25);
		userService.createUser("Charlie", "charlie@example.com",23);
		userService.createUser("Dylan", "dylan@example.com",21);
	}
}
