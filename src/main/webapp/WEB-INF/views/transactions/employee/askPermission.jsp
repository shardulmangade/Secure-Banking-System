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
	
	<a href="<c:url value="/j_spring_security_logout" />"> Logout</a>

	<form:form method="POST" commandName="signupuser"  action="${pageContext.request.contextPath}/transactions/regularEmployee/makeUsersActive">
		<c:choose>
			<c:when test="${not empty users}">
				<table border="1">
				<tr>
					<th> Users </th>
					<th> Action </th>
				</tr>
				<c:forEach var="request" items="${users}">
					<tr>
						<td>${request}</td>
						<td> <input type="checkbox" name="euser" value="${request}"> </td>
					</tr>
					</c:forEach>
				</table>
				<button class="btn btn-large btn-primary" name="action" type="submit" value="approve">Ask For Permission</button>
			</c:when>
			<c:otherwise>
				Either there are no users active with this bank OR all of the users have given you a permission to view their transactions
			</c:otherwise>
		</c:choose>
	</form:form>
	

</body>
</html>