<%-- student-list.jsp - 學生列表頁面 --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>學生列表</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>學生管理系統</h1>
    
    <table>
        <tr>
            <th>編號</th>
            <th>姓名</th>
            <th>年齡</th>
            <th>電子郵件</th>
            <th>操作</th>
        </tr>
        
        <%-- 使用 JSTL 顯示學生列表 --%>
        <c:forEach var="student" items="${students}">
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.age}</td>
                <td>${student.email}</td>
                <td>
                    <a href="student?action=edit&id=${student.id}">編輯</a>
                    <a href="student?action=delete&id=${student.id}">刪除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    
    <br>
    <a href="add-student.jsp">新增學生</a>
</body>
</html>