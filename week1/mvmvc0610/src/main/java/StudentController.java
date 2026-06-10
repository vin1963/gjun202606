
// StudentServlet.java - 學生控制器
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.*;
import java.util.*;
@WebServlet("/student")
public class StudentController extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. 接收使用者請求
        String action = request.getParameter("action");
        
        if ("list".equals(action)) {
            // 2. 處理業務邏輯
            List<Student> students = getStudentList();
            
            // 3. 把資料傳給 JSP
            request.setAttribute("students", students);
            
            // 4. 轉發到 JSP 顯示
            request.getRequestDispatcher("student-list.jsp")
                   .forward(request, response);
        }
    }
    
    private List<Student> getStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "小明", 20, "ming@email.com"));
        students.add(new Student(2, "小華", 21, "hua@email.com"));
        return students;
    }
}
