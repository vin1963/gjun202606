package model;

//StudentDAO.java - 資料存取物件

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentDAO {
 // 使用記憶體儲存（實際專案會用資料庫）
 private static final Map<Integer, Student> students = new ConcurrentHashMap<>();
 private static final AtomicInteger idGenerator = new AtomicInteger(1);
 
 // 初始化範例資料
 static {
     students.put(1, new Student(1, "張小明", 20, "ming@example.com"));
     students.put(2, new Student(2, "李小華", 21, "hua@example.com"));
     students.put(3, new Student(3, "王小美", 19, "mei@example.com"));
     idGenerator.set(4);
 }
 
 // 查詢所有學生
 public List<Student> findAll() {
     return new ArrayList<>(students.values());
 }
 
 // 根據 ID 查詢學生
 public Student findById(int id) {
     return students.get(id);
 }
 
 // 新增或更新學生
 public Student save(Student student) {
     if (student.getId() == 0) {
         // 新增學生
         int newId = idGenerator.getAndIncrement();
         student.setId(newId);
     }
     students.put(student.getId(), student);
     return student;
 }
 
 // 刪除學生
 public boolean delete(int id) {
     return students.remove(id) != null;
 }
 
 // 根據姓名搜尋學生
 public List<Student> findByName(String name) {
     List<Student> result = new ArrayList<>();
     for (Student student : students.values()) {
         if (student.getName().contains(name)) {
             result.add(student);
         }
     }
     return result;
 }
 
 // 檢查電子郵件是否已存在
 public boolean emailExists(String email, int excludeId) {
     for (Student student : students.values()) {
         if (student.getId() != excludeId && 
             student.getEmail().equalsIgnoreCase(email)) {
             return true;
         }
     }
     return false;
 }
}
