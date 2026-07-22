package com.example.demo.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//@RestController = @Controller + @ResponseBody（回傳 JSON）
@RestController
@RequestMapping("/api/users")     // 所有端點前綴 /api/users
public class UserController implements CommandLineRunner {
	 // ConcurrentHashMap：執行緒安全的 HashMap，不需要資料庫
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    // AtomicLong：執行緒安全的 Long，用來產生自動遞增 ID
    private final AtomicLong idGen = new AtomicLong(1);
    
    // ========== GET /api/users — 查詢全部 ==========
    @GetMapping
    public List<User> getAll() {
        // values() 取得所有 User，包裝為 List 回傳
        return List.copyOf(store.values());
    }
    // ========== GET /api/users/{id} — 查詢單筆 ==========
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        // @PathVariable：從 URL 路徑取值，如 /api/users/5 → id=5
        return store.containsKey(id)
            ? ResponseEntity.ok(store.get(id))
            : ResponseEntity.notFound().build();
        // ResponseEntity：可控制 HTTP 狀態碼與標頭
        // ok() → 200 OK，notFound() → 404 Not Found
    }
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		store.put(1L,new User(1L,"Alice","alice@example.com"));
		store.put(2L,new User(2L,"Jimmy","jimmy@example.com"));
		store.put(3L,new User(3L,"Kate","kate@example.com"));
	}
}
