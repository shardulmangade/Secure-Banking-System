<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Requests Approved</title>
</head>
<body>
	Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
Requests Approved
<% response.setHeader("Refresh", "8;/sundevilbank"); %>
</body>
</html>