<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆成功</title>
</head>
<body>
	<%--这是登陆失败以后进入的页面 --%>
	<h1><font color="red"><%=session.getAttribute("err") %></font></h1>
	<h3><a href = "registered.html">现在去注册吧！</a></h3>
	<h3><a href = "homePage.html">重新去登陆吧！</a></h3>
</body>
</html>