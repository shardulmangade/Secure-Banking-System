<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	Welcome emp_name, Below are the user requests:
</h3>

<c:choose>
<c:when test="${not empty userRequests}">
<table border="1">
<tr>
<td>Username</td>
<td>Account Type</td>
<td>First Name</td>
<td>Last Name</td>
<td>Email</td>
<td>Date of Birth</td>
</tr>
<c:forEach var="request" items="${userRequests}">
<tr>
<td>${request.username}</td>
<td>${request.accountType}</td>
<td>${request.firstName}</td>
<td><c:out value="${request.lastName}"></c:out></td>
<td>${request.email}</td>
<td>${request.dateOfBirth}</td>
</tr>
</c:forEach>
</table>
</c:when>
<c:otherwise>
	No new user requests for you to handle !
</c:otherwise>
</c:choose>


</body>
</html>
