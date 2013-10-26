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
<c:when test="${not empty userRequests}">
<table border="1">
	<tr>
		<td>Select</td>
		<td>Username</td>
		<td>Requested By</td><%-- Can add first name last name, how to care about PII --%>
	</tr>
	<c:forEach var="request" items="${userRequests}">
	<tr>
		<td>
			<input type="checkbox" name="selected" value=<c:out value="${request.userName}"/>></input>  
		</td>
		<td>${request.userName}</td>
		<td>${request.department}</td>
  	</tr>
	</c:forEach>
	<tr>
		<td colspan="2">
		<div class ="pull-center">
			<button class="btn btn-large btn-primary" name="action" type="submit" value="approve">Approve</button>
			<button class="btn btn-large btn-primary" name="action" type="submit" value="deny">Deny</button>
		</div>
		</td>
	</tr>
</table>
<br>

</c:when>
<c:otherwise>
	No new user requests for you to handle !
</c:otherwise>
</c:choose>
</form:form>
</body>
</html>