package com.example.demo.service;

import com.example.demo.model.User2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserRepository {
    
    private final List<User2> users = new CopyOnWriteArrayList<>();
    
    public User2 save(User2 user) {
        if (user.getId() == null) {
            user.setId(java.util.UUID.randomUUID().toString());
        }
        // 更新或新增
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        return user;
    }
    
    public Optional<User2> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }
    
    public List<User2> findAll() {
        return new ArrayList<>(users);
    }
    
    public boolean deleteById(String id) {
        return users.removeIf(user -> user.getId().equals(id));
    }
    
    public long count() {
        return users.size();
    }
}
