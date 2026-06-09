<%-- student-list.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>學生管理系統</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            text-align: center;
        }
        .search-section {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .search-form {
            display: flex;
            gap: 10px;
            align-items: center;
            flex-wrap: wrap;
        }
        .search-form input {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            flex: 1;
            min-width: 200px;
        }
        .table-container {
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th {
            background-color: #3498db;
            color: white;
            padding: 15px 10px;
            text-align: left;
            font-weight: bold;
        }
        td {
            padding: 12px 10px;
            border-bottom: 1px solid #eee;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .actions {
            white-space: nowrap;
        }
        .btn {
            padding: 6px 12px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            font-size: 14px;
            display: inline-block;
        }
        .btn-primary { background-color: #3498db; color: white; }
        .btn-warning { background-color: #f39c12; color: white; }
        .btn-danger { background-color: #e74c3c; color: white; }
        .btn-success { background-color: #27ae60; color: white; }
        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #c3e6cb;
        }
        .stats {
            background: white;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 標題區域 -->
        <div class="header">
            <h1>👨‍🎓 學生管理系統 👩‍🎓</h1>
            <p>管理學生資料的最佳工具</p>
        </div>
        
        <!-- 成功訊息顯示 -->
        <c:if test="${param.success == 'add'}">
            <div class="success-message">✅ 學生資料新增成功！</div>
        </c:if>
        <c:if test="${param.success == 'update'}">
            <div class="success-message">✅ 學生資料更新成功！</div>
        </c:if>
        <c:if test="${param.success == 'delete'}">
            <div class="success-message">✅ 學生資料刪除成功！</div>
        </c:if>
        
        <!-- 統計資訊 -->
        <div class="stats">
            <strong>📊 統計資訊：</strong>
            目前共有 <strong style="color: #3498db;">${students.size()}</strong> 位學生
            <c:if test="${not empty searchName}">
                ，搜尋「${searchName}」的結果
            </c:if>
        </div>
        
        <!-- 搜尋區域 -->
        <div class="search-section">
            <h3>🔍 搜尋學生</h3>
            <form method="get" action="student" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" 
                       name="name" 
                       placeholder="請輸入學生姓名..." 
                       value="${searchName}">
                <button type="submit" class="btn btn-primary">🔍 搜尋</button>
                <a href="student?action=list" class="btn btn-secondary">📋 顯示全部</a>
                <a href="student?action=add" class="btn btn-success">➕ 新增學生</a>
            </form>
        </div>
        
        <!-- 學生列表 -->
        <div class="table-container">
            <c:choose>
                <c:when test="${not empty students}">
                    <table>
                        <thead>
                            <tr>
                                <th>編號</th>
                                <th>姓名</th>
                                <th>年齡</th>
                                <th>電子郵件</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}" varStatus="status">
                                <tr>
                                    <td>${student.id}</td>
                                    <td>${student.name}</td>
                                    <td>${student.age} 歲</td>
                                    <td>${student.email}</td>
                                    <td class="actions">
                                        <a href="student?action=edit&id=${student.id}" 
                                           class="btn btn-warning" 
                                           title="編輯">✏️ 編輯</a>
                                        <a href="javascript:deleteStudent(${student.id}, '${student.name}')" 
                                           class="btn btn-danger" 
                                           title="刪除">🗑️ 刪除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="no-data">
                        <h3>😔 沒有找到學生資料</h3>
                        <p>
                            <c:choose>
                                <c:when test="${not empty searchName}">
                                    沒有找到姓名包含「${searchName}」的學生
                                </c:when>
                                <c:otherwise>
                                    系統中還沒有任何學生資料
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <a href="student?action=add" class="btn btn-primary">➕ 新增第一位學生</a>
                        <c:if test="${not empty searchName}">
                            <a href="student?action=list" class="btn btn-secondary">📋 查看所有學生</a>
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        
        <!-- 頁面底部 -->
        <div style="text-align: center; margin-top: 30px;">
            <a href="index.jsp" class="btn btn-secondary">🏠 回到首頁</a>
        </div>
    </div>

    <script>
        // 刪除學生的確認對話框
        function deleteStudent(id, name) {
            if (confirm('確定要刪除學生「' + name + '」嗎？\n\n此操作無法復原！')) {
                window.location.href = 'student?action=delete&id=' + id;
            }
        }
        
        // 自動隱藏成功訊息
        setTimeout(function() {
            const successMsg = document.querySelector('.success-message');
            if (successMsg) {
                successMsg.style.transition = 'opacity 0.5s';
                successMsg.style.opacity = '0';
                setTimeout(() => successMsg.remove(), 500);
            }
        }, 3000);
    </script>
</body>
</html>