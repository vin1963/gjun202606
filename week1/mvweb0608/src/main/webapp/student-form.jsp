<%-- student-form.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:choose><c:when test="${isEdit}">編輯學生</c:when><c:otherwise>新增學生</c:otherwise></c:choose></title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .form-container {
            max-width: 500px;
            margin: 20px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"], input[type="email"], input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        input:focus {
            outline: none;
            border-color: #3498db;
            box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
        }
        .btn {
            padding: 12px 24px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .error-list {
            background-color: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .error-list ul {
            margin: 0;
            padding-left: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h1>
            <c:choose>
                <c:when test="${isEdit}">📝 編輯學生資料</c:when>
                <c:otherwise>➕ 新增學生</c:otherwise>
            </c:choose>
        </h1>
        
        <%-- 顯示錯誤訊息 --%>
        <c:if test="${not empty errors}">
            <div class="error-list">
                <strong>請修正以下錯誤：</strong>
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        
        <form method="post" action="student">
            <c:choose>
                <c:when test="${isEdit}">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${student.id}">
                </c:when>
                <c:otherwise>
                    <input type="hidden" name="action" value="save">
                </c:otherwise>
            </c:choose>
            
            <div class="form-group">
                <label for="name">姓名 *</label>
                <input type="text" 
                       id="name" 
                       name="name" 
                       value="${isEdit ? student.name : param.name}" 
                       required 
                       maxlength="50"
                       placeholder="請輸入學生姓名">
            </div>
            
            <div class="form-group">
                <label for="age">年齡 *</label>
                <input type="number" 
                       id="age" 
                       name="age" 
                       value="${isEdit ? student.age : param.age}" 
                       required 
                       min="1" 
                       max="150"
                       placeholder="請輸入年齡">
            </div>
            
            <div class="form-group">
                <label for="email">電子郵件 *</label>
                <input type="email" 
                       id="email" 
                       name="email" 
                       value="${isEdit ? student.email : param.email}" 
                       required 
                       maxlength="100"
                       placeholder="請輸入電子郵件地址">
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    <c:choose>
                        <c:when test="${isEdit}">💾 更新學生</c:when>
                        <c:otherwise>💾 儲存學生</c:otherwise>
                    </c:choose>
                </button>
                <a href="student?action=list" class="btn btn-secondary">❌ 取消</a>
            </div>
        </form>
    </div>

    <script>
        // 表單驗證
        document.querySelector('form').addEventListener('submit', function(e) {
            const name = document.getElementById('name').value.trim();
            const age = document.getElementById('age').value;
            const email = document.getElementById('email').value.trim();
            
            if (!name) {
                alert('請輸入姓名');
                e.preventDefault();
                return;
            }
            
            if (!age || age < 1 || age > 150) {
                alert('請輸入有效的年齡（1-150）');
                e.preventDefault();
                return;
            }
            
            if (!email || !email.includes('@')) {
                alert('請輸入有效的電子郵件地址');
                e.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>