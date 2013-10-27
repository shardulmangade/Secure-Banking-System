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
	Want to leave ${username}?
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>
	<h3>${username},</h3>
<form:form method="POST" commandName="signupuser"  action="${pageContext.request.contextPath}/it/handlePendingRequestsResponse.html" >
<c:choose>
<c:when test="${not empty userRequests}">
<table border="1">
	<tr>
		<td>Username</td>
		<td>Amount</td>
	</tr>
	<c:forEach var="transaction" items="${listTransactions}">
	<tr>
<%-- 		<td>
			<input type="checkbox" name="selected" value=<c:out value="${request.username}"/>></input>  
		</td>
--%>
		<td>${transaction.fromCustomer}</td>
		<td>${transaction.ammount}</td>
  	</tr>
	</c:forEach>
	<tr>
		<td colspan="2">
		<div class ="pull-center">
			<button class="btn btn-large btn-primary" name="action" type="submit" value="submit">Submit</button>
		</div>
		</td>
	</tr>
</table>
<br>

</c:when>
<c:otherwise>
	No new Customer pending transactions for you to handle !
</c:otherwise>
</c:choose>
</form:form>
</body>
</html>