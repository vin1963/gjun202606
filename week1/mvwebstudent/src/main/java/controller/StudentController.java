package controller;

//StudentServlet.java - 完整版本
import model.Student;
import model.StudentDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/student")
public class StudentController extends HttpServlet {
 
 private StudentDAO studentDAO;
 
 @Override
 public void init() throws ServletException {
     studentDAO = new StudentDAO();
 }
 
 @Override
 protected void doGet(HttpServletRequest request, 
                     HttpServletResponse response) 
         throws ServletException, IOException {
     
     String action = request.getParameter("action");
     if (action == null) action = "list";
     
     try {
         switch (action) {
             case "list":
                 listStudents(request, response);
                 break;
             case "add":
                 showAddForm(request, response);
                 break;
             case "edit":
                 showEditForm(request, response);
                 break;
             case "delete":
                 deleteStudent(request, response);
                 break;
             case "search":
                 searchStudents(request, response);
                 break;
             default:
                 listStudents(request, response);
         }
     } catch (Exception e) {
         e.printStackTrace();
         request.setAttribute("error", "系統發生錯誤：" + e.getMessage());
         request.getRequestDispatcher("error.jsp").forward(request, response);
     }
 }
 
 @Override
 protected void doPost(HttpServletRequest request, 
                      HttpServletResponse response) 
         throws ServletException, IOException {
     
     // 設定編碼
     request.setCharacterEncoding("UTF-8");
     
     String action = request.getParameter("action");
     
     try {
         switch (action) {
             case "save":
                 saveStudent(request, response);
                 break;
             case "update":
                 updateStudent(request, response);
                 break;
             default:
                 doGet(request, response);
         }
     } catch (Exception e) {
         e.printStackTrace();
         request.setAttribute("error", "處理請求時發生錯誤：" + e.getMessage());
         request.getRequestDispatcher("error.jsp").forward(request, response);
     }
 }
 
 // 顯示學生列表
 private void listStudents(HttpServletRequest request, 
                          HttpServletResponse response) 
         throws ServletException, IOException {
     
     List<Student> students = studentDAO.findAll();
     request.setAttribute("students", students);
     request.getRequestDispatcher("student-list.jsp").forward(request, response);
 }
 
 // 顯示新增表單
 private void showAddForm(HttpServletRequest request, 
                         HttpServletResponse response) 
         throws ServletException, IOException {
     
     request.getRequestDispatcher("student-form.jsp").forward(request, response);
 }
 
 // 顯示編輯表單
 private void showEditForm(HttpServletRequest request, 
                          HttpServletResponse response) 
         throws ServletException, IOException {
     
     int id = Integer.parseInt(request.getParameter("id"));
     Student student = studentDAO.findById(id);
     
     if (student == null) {
         request.setAttribute("error", "找不到學生資料");
         listStudents(request, response);
         return;
     }
     
     request.setAttribute("student", student);
     request.setAttribute("isEdit", true);
     request.getRequestDispatcher("student-form.jsp").forward(request, response);
 }
 
 // 儲存新學生
 private void saveStudent(HttpServletRequest request, 
                        HttpServletResponse response) 
         throws ServletException, IOException {
     
     String name = request.getParameter("name");
     String ageStr = request.getParameter("age");
     String email = request.getParameter("email");
     
     // 驗證輸入
     List<String> errors = validateStudentInput(name, ageStr, email, 0);
     if (!errors.isEmpty()) {
         request.setAttribute("errors", errors);
         request.setAttribute("name", name);
         request.setAttribute("age", ageStr);
         request.setAttribute("email", email);
         request.getRequestDispatcher("student-form.jsp").forward(request, response);
         return;
     }
     
     // 檢查電子郵件是否重複
     if (studentDAO.emailExists(email, 0)) {
         errors.add("電子郵件已存在");
         request.setAttribute("errors", errors);
         request.setAttribute("name", name);
         request.setAttribute("age", ageStr);
         request.setAttribute("email", email);
         request.getRequestDispatcher("student-form.jsp").forward(request, response);
         return;
     }
     
     // 建立學生物件
     Student student = new Student(0, name, Integer.parseInt(ageStr), email);
     studentDAO.save(student);
     
     // 重導向到列表頁面
     response.sendRedirect("student?action=list&success=add");
 }
 
 // 更新學生資料
 private void updateStudent(HttpServletRequest request, 
                           HttpServletResponse response) 
         throws ServletException, IOException {
     
     int id = Integer.parseInt(request.getParameter("id"));
     String name = request.getParameter("name");
     String ageStr = request.getParameter("age");
     String email = request.getParameter("email");
     
     // 驗證輸入
     List<String> errors = validateStudentInput(name, ageStr, email, id);
     if (!errors.isEmpty()) {
         Student student = new Student(id, name, 
             ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr), email);
         request.setAttribute("student", student);
         request.setAttribute("errors", errors);
         request.setAttribute("isEdit", true);
         request.getRequestDispatcher("student-form.jsp").forward(request, response);
         return;
     }
     
     // 檢查電子郵件是否重複
     if (studentDAO.emailExists(email, id)) {
         errors.add("電子郵件已存在");
         Student student = new Student(id, name, Integer.parseInt(ageStr), email);
         request.setAttribute("student", student);
         request.setAttribute("errors", errors);
         request.setAttribute("isEdit", true);
         request.getRequestDispatcher("student-form.jsp").forward(request, response);
         return;
     }
     
     // 更新學生資料
     Student student = new Student(id, name, Integer.parseInt(ageStr), email);
     studentDAO.save(student);
     
     // 重導向到列表頁面
     response.sendRedirect("student?action=list&success=update");
 }
 
 // 刪除學生
 private void deleteStudent(HttpServletRequest request, 
                           HttpServletResponse response) 
         throws ServletException, IOException {
     
     int id = Integer.parseInt(request.getParameter("id"));
     boolean deleted = studentDAO.delete(id);
     
     String result = deleted ? "delete" : "error";
     response.sendRedirect("student?action=list&success=" + result);
 }
 
 // 搜尋學生
 private void searchStudents(HttpServletRequest request, 
                            HttpServletResponse response) 
         throws ServletException, IOException {
     
     String searchName = request.getParameter("name");
     List<Student> students;
     
     if (searchName != null && !searchName.trim().isEmpty()) {
         students = studentDAO.findByName(searchName.trim());
         request.setAttribute("searchName", searchName);
     } else {
         students = studentDAO.findAll();
     }
     
     request.setAttribute("students", students);
     request.getRequestDispatcher("student-list.jsp").forward(request, response);
 }
 
 // 驗證學生輸入資料
 private List<String> validateStudentInput(String name, String ageStr, 
                                          String email, int excludeId) {
     List<String> errors = new ArrayList<>();
     
     // 驗證姓名
     if (name == null || name.trim().isEmpty()) {
         errors.add("姓名不能為空");
     } else if (name.trim().length() > 50) {
         errors.add("姓名長度不能超過 50 個字元");
     }
     
     // 驗證年齡
     if (ageStr == null || ageStr.trim().isEmpty()) {
         errors.add("年齡不能為空");
     } else {
         try {
             int age = Integer.parseInt(ageStr);
             if (age < 1 || age > 150) {
                 errors.add("年齡必須在 1-150 之間");
             }
         } catch (NumberFormatException e) {
             errors.add("年齡必須是有效的數字");
         }
     }
     
     // 驗證電子郵件
     if (email == null || email.trim().isEmpty()) {
         errors.add("電子郵件不能為空");
     } else if (!email.contains("@") || !email.contains(".")) {
         errors.add("請輸入有效的電子郵件地址");
     } else if (email.length() > 100) {
         errors.add("電子郵件長度不能超過 100 個字元");
     }
     
     return errors;
 }
}
