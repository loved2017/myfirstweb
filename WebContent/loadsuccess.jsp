<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎进入系统</title>
</head>
<body>
<!-- 登陆成功界面 -->
	<h3><font color = "red">你好，<%=session.getAttribute("nickname") %>！欢迎登录虎虎虎系统</font></h3>
</body>
</html>