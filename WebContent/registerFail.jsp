<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册失败</title>
</head>
<body>
<%--登录失败所到达的页面 --%>
	<%
		request.setCharacterEncoding("utf-8");
	%>

	<h1><font color="red"><%=session.getAttribute("err") %></font></h1>
	<h3><a href = "registered.html">重新去注册吧！</a></h3>
</body>
</html>