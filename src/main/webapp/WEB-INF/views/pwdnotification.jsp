<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Signup Page</title>
<style>
.error {
color: #ff0000;
font-style: italic;
}
</style>

</head>
<body>
Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a><br><br>
<P>  Password Change </P>
<br>
<c:if test="${not empty success}">Your password was changed successfully !!! You will be automatically redirected in few seconds...</c:if>
<% response.setHeader("Refresh", "5;/sundevilbank/home"); %>
	

</body>
</html>
