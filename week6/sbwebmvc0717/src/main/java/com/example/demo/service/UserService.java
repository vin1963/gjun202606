package com.example.demo.service;


import com.example.demo.model.User2;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User2 createUser(String name, String email, int age) {
        User2 user = new User2(name, email, age);
        return userRepository.save(user);
    }
    
    public Optional<User2> getUserById(String id) {
        return userRepository.findById(id);
    }
    
    public List<User2> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User2 updateUser(String id, String name, String email, int age) {
        Optional<User2> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User2 user = existingUser.get();
            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            return userRepository.save(user);
        }
        throw new RuntimeException("使用者不存在: " + id);
    }
    
    public boolean deleteUser(String id) {
        return userRepository.deleteById(id);
    }
    
    public long getUserCount() {
        return userRepository.count();
    }
}
