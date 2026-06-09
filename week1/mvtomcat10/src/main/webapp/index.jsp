<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<html>
<body>
<h2><%= "Hello World!" %></h2>
<%
   Date d1=new Date();
%>
<h1>
 Date Time : <%= d1.toLocaleString() %>

</h1>
</body>
</html>
