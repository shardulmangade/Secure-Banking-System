<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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

<form:form method="POST" commandName="signupemployee"  action="${pageContext.request.contextPath}/corporate/pending" >
<c:choose>
<c:when test="${not empty managersList}">
List of active managers:
<table border="1">
	<tr>
		<td>Username</td>
		<td>First Name</td>
		<td>Last Name</td>
		<td>Email</td>
		<td>Department</td>
		<td>Created By</td>
		<td>Action</td>
	</tr>
	<c:forEach var="user" items="${managersList}">
	<tr>
		<td>${user.username}</td>
		<td>${user.firstName}</td>
		<td>${user.lastName}</td>
		<td>${user.email}</td>
		<td>${user.department}</td>
		<td>${user.createdBy}</td>
		<td><a href="${pageContext.servletContext.contextPath}/corporate/deactivate/${user.username}">Deactivate</a></td>
  	</tr>
	</c:forEach>
</table>
<br>

</c:when>
<c:otherwise>
	No Active managers are available !
</c:otherwise>
</c:choose>
</form:form>
</body>
</html>