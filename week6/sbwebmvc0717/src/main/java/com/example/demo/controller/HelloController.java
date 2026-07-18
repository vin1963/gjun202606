package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.model.User;

@Controller
@RequestMapping("/api")
public class HelloController {
	
     @GetMapping("/index")
     public String index(Model model) {
		 model.addAttribute("title", "Spring Boot Title!");
		 model.addAttribute("message", "Spring Boot Message!");
		 return "index";
	 }
     
     @GetMapping("/myrole")
     public String myrole(Model model) {
    	 		 model.addAttribute("role", "user");
    	 		 return "myrole";
     }
     @GetMapping("/items")
     public String myitems(Model model) {
    	         List<String> items = List.of("Item 1", "Item 2", "Item 3");
    	 		 model.addAttribute("items", items);
    	 		 return "item";
     }
     
     @GetMapping("/users")
     public String myusers(Model model) {
    	         List<User> items = List.of(new User("Alice","alice@test.com"),new User("Bob","bob@test.com"),new User("Cindy","cindy@test.com"));
    	 		 model.addAttribute("users", items);
    	 		 return "user";
     }
     @PostMapping("/users")
     public String addUser(@ModelAttribute User user, Model model) {		 
		 // 这里可以将新用户添加到数据库或列表中
		 model.addAttribute("user", user);
		 return "muser";
	 }
}
